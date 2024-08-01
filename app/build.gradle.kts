plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.restaurantorderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.restaurantorderapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.mediarouter)
    implementation(libs.activity)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.legacy.support.v4)
    implementation(libs.google.material)
    implementation(libs.viewpager2)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.gson)
    implementation(libs.work.runtime)
    implementation(libs.glide)
    implementation(libs.androidx.recyclerview)
    annotationProcessor(libs.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}