package kz.solva.migration.config

import kz.solva.migration.model.DatabaseProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

  @Bean
  fun databaseProperties(
      @Value("\${database.driverClassName}") driverClassName: String,
      @Value("\${database.url}") url: String,
      @Value("\${database.username}") username: String,
      @Value("\${database.password}") password: String,
  ) = DatabaseProperties(driverClassName, url, username, password)

  @Bean
  fun dataSource(databaseProperties: DatabaseProperties): DataSource = DriverManagerDataSource().apply {
    setDriverClassName(databaseProperties.driverClassName)
    url = databaseProperties.url
    username = databaseProperties.username
    password = databaseProperties.password
  }
}
