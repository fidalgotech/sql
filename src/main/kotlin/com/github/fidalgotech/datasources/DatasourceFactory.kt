package com.github.fidalgotech.datasources

import com.github.fidalgotech.JdbcConfig
import javax.sql.DataSource

interface DatasourceFactory {
    fun create(jdbcConfig: JdbcConfig): DataSource
    companion object {
        val AGROAL = AgroalDatasourceFactory()
    }
}