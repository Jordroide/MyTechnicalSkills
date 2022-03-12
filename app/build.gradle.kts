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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").res.srcDirs(
            "src/main/res/browsing",
            "src/main/res/common",
            "src/main/res/gallery",
            "src/main/res/quote",
        )
    }
}

dependencies {

    /** Androidx dependencies */
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    /** Google dependencies */
    implementation("com.google.android.material:material:1.5.0")
    /** JetPack dependencies */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.room:room-ktx:2.4.2")
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    kapt("androidx.room:room-compiler:2.4.2")
    /** Kotlin dependencies */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    /** Third part dependencies */
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil:2.0.0-rc01")
    /** Test dependencies */
    /** Androidx dependencies */
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    /** Test framework */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("io.mockk:mockk:1.11.0")
    /** Kotlin dependencies */
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
}
