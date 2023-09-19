package kz.solva.migration.model

data class DatabaseProperties(
  val driverClassName: String,
  val url: String,
  val username: String,
  val password: String,
)
