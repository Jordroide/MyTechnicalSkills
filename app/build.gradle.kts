plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("de.mannodermaus.android-junit5")
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
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
            isShrinkResources = false
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

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        getByName("main").res.srcDirs(
            "src/main/res/browsing",
            "src/main/res/common",
            "src/main/res/cv",
            "src/main/res/gallery",
            "src/main/res/quote",
        )
    }

    testOptions {
        junitPlatform {
            filters {
                includeEngines("spek2")
            }
            jacocoOptions {
                html.enabled(true)
                xml.enabled(false)
                csv.enabled(false)
            }
        }
        unitTests.all {
            it.testLogging.events("passed", "skipped", "failed")
        }
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
    implementation("androidx.core:core-splashscreen:1.0.0-beta01")
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
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
    androidTestImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    androidTestRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")
    /** Test framework */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("io.mockk:mockk-agent-jvm:1.11.0")
    // spek
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.10")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.10")
    /** Kotlin dependencies */
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    // spek requires kotlin-reflect, omit when already in classpath
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
}
