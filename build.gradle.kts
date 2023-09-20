import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths


val kotlinVersion: String by extra
val implementation by configurations
val springBootVersion: String by extra

plugins {
  `kotlin-dsl`
  id("io.spring.dependency-management") version "1.1.2"
  id("nu.studer.jooq") version "8.2.1"
  kotlin("jvm") version "1.8.20"
  kotlin("plugin.spring") version "1.8.20"
  kotlin("kapt") version "1.8.20"
}

repositories {
  mavenCentral()
}

group = "kz.solva"
version = "0.0.1-SNAPSHOT"

fun path(project: Project, folderName: String) =
  Files.isDirectory(Paths.get(project.projectDir.absolutePath, "src/main/$folderName"))

fun kotlinProjects() = subprojects.filter { project -> path(project, "kotlin") }
fun modelProjects() = subprojects.filter { project -> Regex("[a-z]+-model").matches(project.projectDir.name) }

tasks.build {
  dependsOn(":migration:build")
  finalizedBy(":migration:runMigrations")
}

allprojects {
  repositories {
    mavenCentral()
  }
}

configure(kotlinProjects()) {
  apply(plugin = "kotlin")
  apply(plugin = "kotlin-kapt")
  apply(plugin = "kotlin-spring")

  dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jooq:jooq:3.18.6")
    implementation("io.projectreactor:reactor-core:3.5.9")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")
  }

  tasks {
    withType<KotlinCompile> {
      kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
      }
    }

    withType<Test> {
      useJUnitPlatform()
    }
  }
}

configure(modelProjects()) {
  apply(plugin = "nu.studer.jooq")
  dependencies {
    jooqGenerator("org.postgresql:r2dbc-postgresql:1.0.2.RELEASE")
  }
}
