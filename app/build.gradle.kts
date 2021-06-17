plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 1

android {
    compileSdkVersion = "android-S"
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.jordroid.showcase"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode (versionMajor * 10000 + versionMinor * 100 + versionPatch * 1)
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    /** Androidx dependencies */
    implementation("androidx.activity:activity-ktx:1.2.3")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    /** Google dependencies */
    implementation("com.google.android.material:material:1.3.0")
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
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    /** Test framework */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("io.mockk:mockk:1.11.0")
    /** Kotlin dependencies */
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")

}