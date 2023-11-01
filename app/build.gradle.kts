plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.instalens"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.instalens"
        minSdk = 28
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Allow references to generated code for Dagger-Hilt
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Dependency Versions
    val splashScreenVersion = "1.0.1"
    val dataStoreVersion = "1.0.0"
    val navComposeVersion = "2.6.0"
    val daggerHiltVersion = "2.45"
    val tfLiteVersion = "0.4.0"
    val cameraxVersion = "1.3.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Splash-Screen Dependency
    implementation("androidx.core:core-splashscreen:$splashScreenVersion")

    // Preferences DataStore Dependency
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //Compose Navigation
    implementation("androidx.navigation:navigation-compose:$navComposeVersion")

    // Dagger Hilt Dependencies
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Tensorflow-Lite Dependencies
    implementation("org.tensorflow:tensorflow-lite-task-vision:$tfLiteVersion")
    implementation("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:$tfLiteVersion")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.9.0")

    // Camera-X Dependencies
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")

    // Accompanist Permission manager Dependency
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}