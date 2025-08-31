plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
}

val major = 1
val minor = 3
val patch = 5

val generatedCode = major * 10000 + minor * 100 + patch
val generatedName = "${major}.${minor}.${patch}"

android {
    namespace = "com.ramo.quran"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ramo.quran"
        minSdk = 23
        targetSdk = 36
        versionCode = generatedCode
        versionName = generatedName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    signingConfigs {

        create("stage") {
            storeFile = file("../.keystone/stage_keystone.jks")
            storePassword = "5ZiWq3Yt2oYyX9"
            keyAlias = "key0"
            keyPassword = "5ZiWq3Yt2oYyX9"

            enableV1Signing = true
            enableV2Signing = true
        }
    }


    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("stage")
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "@string/app_name_dev")
        }
        create("prod") {
            dimension = "version"
            resValue("string", "app_name", "@string/app_name_production")
        }
    }
    lint {
        disable += "Instantiatable"
    }

    buildFeatures {
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    val kotlinVersion: String by rootProject.extra
    val hiltVersion: String by rootProject.extra

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.3")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation(project(mapOf("path" to ":core")))
    testImplementation("junit:junit:4.13.2")
    //androidTestImplementation("androidx.test.ext:junit:1.1.3")
    //androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //for locale
    implementation("com.github.YarikSOffice:lingver:1.3.0")

    // room db
    val roomVersion = "2.7.2"

    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("com.github.ramazanogunc:Android-Sweet-Recycler-Adapter:2.0.2")

    // firebase for analytic and crashlytics
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-database")


    // for di
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")


    // Gson for json convert
    implementation("com.google.code.gson:gson:2.13.1")

    // for pagination
    val pagingVersion = "3.3.6"

    implementation("androidx.paging:paging-runtime:$pagingVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    // for Feedback

}

