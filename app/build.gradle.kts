import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.movies"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.movies"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    // appcompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    //dagger
    implementation("com.google.dagger:dagger:2.46.1")
    kapt("com.google.dagger:dagger-compiler:2.46.1")

    // gson
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.14.2")

    // ktx
    implementation("androidx.core:core-ktx:1.10.1")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ui
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // viewbinding delegate
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")
}