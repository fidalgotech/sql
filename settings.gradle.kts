
rootProject.name = "sql"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("jooq", "org.jooq:jooq:3.18.7")
            library("agroal", "io.agroal:agroal-pool:2.2")
            library("flyway", "org.flywaydb:flyway-core:10.0.1")
        }

        create("testLibs") {
            library("jupiter", "org.junit.jupiter:junit-jupiter:5.9.2")
            library("jupiter.api", "org.junit.jupiter:junit-jupiter-api:5.9.2")
            library("jupiter.engine", "org.junit.jupiter:junit-jupiter-engine:5.9.2")
        }
    }
}

