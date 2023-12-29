plugins {
    id("com.android.application") version "8.2.0"
    id("org.jetbrains.kotlin.android") version "1.9.21" // Updated Kotlin plugin to match your Kotlin version
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0-Beta2"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" // Use a version compatible with your Kotlin version
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.jetbrains.dokka") version "1.9.10"
}
tasks.dokkaHtml {
    outputDirectory.set(file("$rootDir/docs"))
}
ktlint {
    verbose.set(true)
    android.set(false)
}
android {
    namespace = "com.pieterbommele.dunkbuzz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pieterbommele.dunkbuzz"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7" // Updated to match Kotlin 1.9.22
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.runtime:runtime:1.5.4") // Updated for compatibility
    implementation("androidx.compose.ui:ui:1.5.4") // Updated for compatibility
    implementation("androidx.compose.ui:ui-graphics:1.5.4") // Updated for compatibility
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4") // Updated for compatibility
    implementation("androidx.compose.material3:material3:1.1.2") // Check for the latest stable version

    implementation("androidx.navigation:navigation-compose:2.7.6") // Updated for compatibility

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")

    val room_version = "2.6.1" // Check for the latest stable version
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    implementation("androidx.work:work-runtime-ktx:2.9.0") // Check for the latest stable version

    testImplementation("androidx.room:room-testing:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4") // Updated for compatibility
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6") // Updated for compatibility
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4") // Updated for compatibility
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4") // Updated for compatibility
}
