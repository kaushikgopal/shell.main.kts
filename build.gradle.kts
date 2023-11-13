plugins {
  java
  kotlin("jvm")  version "1.8.22"
  `maven-publish`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("script-runtime"))
}

tasks {
//  compileKotlin {
//    kotlinOptions.jvmTarget = "1.8"
//  }
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
