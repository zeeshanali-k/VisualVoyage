import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("plugin.serialization") version "1.9.0"
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")

    id("app.cash.sqldelight") version "2.0.0"
}

kotlin {
    androidTarget()

    jvm("desktop")


    js(IR) {
        browser()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            export(Deps.Calf.ui)
        }
    }


    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(Deps.SQLDelight.jvmDriver)
                implementation(Deps.KtorClient.jvm)

                implementation(compose.desktop.common)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(Deps.KtorClient.web)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
//                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // For Adaptive UI components
                with(Deps.Calf) {
                    api(ui)
                }

                implementation("co.touchlab:stately-common:2.0.5")
                //Typist for typing animation
                api(Deps.TYPIST_CMP)

                //Ktor Client for http communication
                with(Deps.KtorClient) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(serializationKotlinxJson)
                }
                implementation(Deps.KOTLINX_SERIALISATION_JSON)

                //for date and time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

                //dependency injection
                with(Deps.Koin) {
                    api(core)
                    api(compose)
                    api(test)
                }
                //view model and flow etc
                with(Deps.Moko) {
                    api(mvvmCore)
                    api(mvvmCompose)
                    api(mvvmFlowCompose)
                }
//                with(Deps.Voyager) {
//                    implementation(navigator)
//                }

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.8.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")

                implementation(Deps.SQLDelight.androidDriver)
                implementation(Deps.KtorClient.android)

                implementation("androidx.camera:camera-camera2:1.3.1")
                implementation("androidx.camera:camera-lifecycle:1.3.1")
                implementation("androidx.camera:camera-view:1.3.1")
                implementation("com.google.accompanist:accompanist-permissions:0.30.1")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Deps.SQLDelight.nativeDriver)
                implementation(Deps.KtorClient.native)
            }
        }
    }
}


//compose.experimental {
//    web.application {}
//}

sqldelight {
    databases {
        create("Classy") {
            packageName.set("com.devscion.classy.db")
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Classy"
            macOS {
                bundleID = "com.devscion.classy"
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.devscion.classy"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
