plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 5

android {

    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.jordroid.showcase"
        minSdk = 21
        targetSdk = 31
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch * 1
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-debug"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    /** Androidx dependencies */
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")
    /** Google dependencies */
    implementation("com.google.android.material:material:1.4.0")
    /** JetPack dependencies */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.room:room-ktx:2.3.0")
    implementation("androidx.room:room-runtime:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    kapt("androidx.room:room-compiler:2.3.0")
    /** Kotlin dependencies */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    /** Third part dependencies */
    implementation("io.insert-koin:koin-android:3.0.2")
    implementation("com.squareup.retrofit2:retrofit:2.8.0")
    implementation("com.squareup.retrofit2:converter-gson:2.8.0")

    /** Test dependencies */
    /** Androidx dependencies */
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    /** Test framework */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("io.mockk:mockk:1.11.0")
    /** Kotlin dependencies */
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
}
