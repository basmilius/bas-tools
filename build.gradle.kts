plugins {
    id("org.jetbrains.intellij") version "0.4.26"
    kotlin("jvm") version "1.4.0"
}

group = "dev.bas"
version = "2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

intellij {
    updateSinceUntilBuild = false
}
