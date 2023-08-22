import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths

plugins {
	`kotlin-dsl`
  id("org.springframework.boot") version "3.1.2"
  id("io.spring.dependency-management") version "1.1.2"
  kotlin("jvm") version "1.8.20"
  kotlin("plugin.spring") version "1.8.20"
  kotlin("kapt") version "1.8.20"
}

repositories {
  mavenCentral()
}

group = "kz.solva"
version = "0.0.1-SNAPSHOT"

val implementation by configurations

fun path(project: Project, folderName: String) =
	Files.isDirectory(Paths.get(project.projectDir.absolutePath, "src/main/$folderName"))
fun kotlinProjects() = subprojects.filter { project -> path(project, "kotlin") }


configure(kotlinProjects()) {
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-spring")

	dependencies {
		implementation(kotlin("stdlib-jdk8"))
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

		repositories {
			mavenCentral()
		}
	}
}
