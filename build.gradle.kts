plugins {
    kotlin("jvm") version "1.8.20"
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.jooq)
    api(libs.agroal)
    api(libs.flyway)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest("1.8.20")
        }
    }
}
