plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
}

repositories {
    mavenCentral()
   // maven (url = "https://maven.google.com/")
}
val androidToolsVersion = "25.3.0"
dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
}
