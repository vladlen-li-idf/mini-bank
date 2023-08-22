val springBootVersion: String by extra

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:${springBootVersion}")
	implementation("org.springframework.boot:spring-boot-starter-jdbc:${springBootVersion}")
	implementation("org.springframework.boot:spring-boot-starter-jooq:${springBootVersion}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${springBootVersion}")
	runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.2.RELEASE")
}
