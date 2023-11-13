plugins {
  java
  kotlin("jvm")  version "1.6.0"
  `maven-publish`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("script-runtime"))
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "sh.kau"
      artifactId = "shell-kts"
      version = "1.0.0"

      from(components["java"])
    }
  }
}
