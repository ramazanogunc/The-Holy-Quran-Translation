// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val kotlinVersion by extra { "2.1.21" }
    val hiltVersion by extra { "2.57.1" }


    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.12.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        // for analytic
        classpath("com.google.gms:google-services:4.4.2")
        // for crashlytics
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        // for di
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(uri("https://jitpack.io"))
    }
}