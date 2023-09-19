package kz.solva.migration

import kz.solva.migration.config.PropertyConfig
import kz.solva.migration.config.DatabaseConfig
import kz.solva.migration.config.MigrationConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class MigrationApplication

fun main() {
  AnnotationConfigApplicationContext(
      PropertyConfig::class.java,
      DatabaseConfig::class.java,
      MigrationConfig::class.java
  )
    .use {
    print("Migration finished!")
  }
}
