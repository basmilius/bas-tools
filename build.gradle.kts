plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    kotlin("jvm") version "1.4.21"
}

group = "dev.bas"
version = "2.2"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    implementation(kotlin("stdlib"))
}

intellij {
    updateSinceUntilBuild = false
    version = "211.4961.33-EAP-SNAPSHOT"
}
