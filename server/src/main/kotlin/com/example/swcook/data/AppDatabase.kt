package com.example.swcook.data

import com.example.swcook.data.model.IngredientTable
import com.example.swcook.data.model.RecipeIngredientTable
import com.example.swcook.data.model.RecipeTable
import com.example.swcook.data.model.StepTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class AppDatabase(private val config: ApplicationConfig) {

    lateinit var dataSource: DataSource

    fun init() {
        connectionPool()
        migration()
        orm()
    }

    private fun connectionPool() {
        val dbConfig = this.config.config("ktor.database")

        val config = HikariConfig()
        config.jdbcUrl = dbConfig.property("connection.jdbc").getString()
        config.username = dbConfig.property("connection.user").getString()
        config.password = dbConfig.property("connection.password").getString()

        config.isAutoCommit = false
        config.maximumPoolSize = 3
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        dataSource = HikariDataSource(config)
    }

    private fun migration() {
        val migrationConfig = this.config.config("ktor.database.migration")

        val table = "classpath:db/migration/table"
        val include = migrationConfig.property("includes").getList().map { path -> "classpath:$path" }
        val locations = arrayOf(table) + include

        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations(*locations)
            .load()
        flyway.migrate()
    }

    private fun orm() {
        Database.connect(dataSource)

        /*transaction {

            val statements = SchemaUtils.createStatements(
                RecipeTable,
                IngredientTable,
                StepTable,
                RecipeIngredientTable
            )
            println("==================================================")
            println(statements.joinToString(separator = "\n"))
            println("==================================================")
        }*/
    }
}
