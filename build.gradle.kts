plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    kotlin("jvm") version "1.4.0"
}

group = "dev.bas"
version = "2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

intellij {
    alternativeIdePath = "/Users/bas/Library/Application Support/JetBrains/Toolbox/apps/PhpStorm/ch-0/203.5981.118/PhpStorm 2020.3 EAP.app/Contents"
    updateSinceUntilBuild = false
}
