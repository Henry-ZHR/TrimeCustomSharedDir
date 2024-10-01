plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "moe.henry_zhr.trime_custom_shared_dir"
    compileSdk = 34

    defaultConfig {
        applicationId = "moe.henry_zhr.trime_custom_shared_dir"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependenciesInfo {
        includeInApk = false
    }
}

dependencies {
    compileOnly(libs.xposed.api)
}