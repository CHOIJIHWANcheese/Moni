plugins {
    application
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.moni"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

javafx {
    version = "25"
    modules = listOf("javafx.controls", "javafx.graphics")
}

sourceSets {
    named("main") {
        resources.srcDir("assets")
    }
}

application {
    mainModule.set("com.moni.app")
    mainClass.set("com.moni.app.MoniApplication")
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics,com.sun.jna")
}

dependencies {
    implementation("net.java.dev.jna:jna:5.19.1")
    implementation("net.java.dev.jna:jna-platform:5.19.1")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
