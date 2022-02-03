// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val kotlinVersion by extra { "1.6.10" }
    val hiltVersion by extra { "2.40.5" }


    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        // for analytic
        classpath("com.google.gms:google-services:4.3.8")
        // for crashlytics
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.0")
        // for di
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(uri("https://jitpack.io"))
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}