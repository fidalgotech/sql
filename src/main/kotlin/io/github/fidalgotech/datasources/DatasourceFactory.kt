package io.github.fidalgotech.datasources

import io.github.fidalgotech.JdbcConfig
import javax.sql.DataSource

interface DatasourceFactory {
    fun create(jdbcConfig: JdbcConfig): DataSource
    companion object {
        val AGROAL = AgroalDatasourceFactory()
    }
}