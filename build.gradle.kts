version = "0.1.0"
val eventuallyJar by tasks.registering(Jar::class)

tasks {
  jar {
    manifest {
      attributes(
              mapOf("Implementation-Title" to project.name,
                      "Implementation-Version" to project.version)
      )
    }
  }
}

artifacts {
  add("archives", eventuallyJar)
}

plugins {
  id("org.jetbrains.kotlin.jvm").version("1.3.10")
  application
}

repositories {
  jcenter()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
  mainClassName = "eventually.kotlin.EventuallyKt"
}
