package com.github.fidalgotech.jooq

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionalCallable
import org.jooq.TransactionalRunnable
import org.jooq.impl.DSL
import javax.sql.DataSource

class DslContext constructor(private val writerDS: DataSource,
                             private val readerDS: DataSource,
                             private val dialect: SQLDialect) {

    val writer: DSLContext = DSL.using(writerDS, dialect)
    val reader: DSLContext = DSL.using(readerDS, dialect)

    fun transactional(function: TransactionalRunnable) {
        writer.transaction(function)
    }

    fun <T> transactionalResult(function: TransactionalCallable<T>): T {
       return writer.transactionResult(function)
    }
}