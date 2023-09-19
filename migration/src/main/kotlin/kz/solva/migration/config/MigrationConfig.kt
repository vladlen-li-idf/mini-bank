package kz.solva.migration.config

import kz.solva.migration.model.LiquibaseProperties
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class MigrationConfig {

  @Bean
  fun liquibaseProperties(
    @Value("\${liquibase.changelog.path}") changelogPath: String
  ) = LiquibaseProperties(changelogPath)

  @Bean
  fun liquibase(
    datasource: DataSource,
    liquibaseProperties: LiquibaseProperties
  ): SpringLiquibase = SpringLiquibase()
    .apply {
      this.dataSource = datasource
      this.changeLog = liquibaseProperties.changelogPath
    }
}
