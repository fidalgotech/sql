package io.github.fidalgotech.flyway

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import java.util.logging.Level
import java.util.logging.Logger
import javax.sql.DataSource

class FlywayMigrate {
    companion object {
        private val RETRY_ATTEMPTS = System.getenv("MIGRATION_ATTEMPTS")?.toInt() ?: 10

        fun migrate(dataSource: DataSource?) {
            migrate(dataSource, false)
        }

        fun migrate(dataSource: DataSource?, testMode: Boolean) {
            val fluentConfiguration = Flyway.configure()
            if (testMode) {
                if (System.getProperty("testData", "true") == "true") {
                    fluentConfiguration.locations("db/migration", "db/test")
                }
            }

            if( isRunningInNativeImage() ) {
                fluentConfiguration.resourceProvider(GraalVMResourceProvider(fluentConfiguration.locations))
            }
            fluentConfiguration.dataSource(dataSource)
            val flyway = fluentConfiguration.load()
            var attempted = 0
            while (attempted < RETRY_ATTEMPTS) {
                try {
                    flyway.migrate()
                    break
                } catch (e: FlywayException) {
                    if (e.message?.contains("lock table")!! || e.message?.contains("schema_version")!!) {
                        throw e
                    }
                    ++attempted
                    if (attempted == RETRY_ATTEMPTS) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to apply migration", e)
                        throw e
                    }
                }
            }
        }
        private fun isRunningInNativeImage(): Boolean {
            return System.getProperty("org.graalvm.nativeimage.imagecode") != null
        }
    }
}