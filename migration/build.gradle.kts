val springVersion: String by extra

dependencies {
  implementation("org.liquibase:liquibase-core:4.23.1")
  implementation("org.postgresql:postgresql:42.6.0")
  implementation("org.springframework:spring-context:${springVersion}")
  implementation("org.springframework:spring-jdbc:${springVersion}")
  implementation("org.springframework:spring-core:${springVersion}")
}
