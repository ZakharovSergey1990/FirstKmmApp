plugins {
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version "1.6.10"
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
   // kotlin("jvm") version "1.6.0"
   // id ("org.jetbrains.kotlin.jvm") version "1.6.0"
}

sqldelight {
    database("TestDatabase") {
        packageName = "com.example.db"
    }
}


version = "1.0"

kotlin {
    android()
    iosX64()
   // iosArm64()
  //  iosSimulatorArm64()

//    val iosTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget = when {
//        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
//        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
//        else -> ::iosX64
//    }
//
//    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
        }
    }
    
    sourceSets {

        val ktorVersion = "1.6.7"
        val napierVersion = "2.4.0"
        val commonMain by getting {
            dependencies {

              //  //ktor /
               implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.6.10")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt"){
//                    version{
//                        strictly("1.6.0-native-mt")
//                    }
                }
                implementation("com.squareup.sqldelight:runtime:1.5.3")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")
                //di
                implementation("org.kodein.di:kodein-di:7.10.0")

            //    implementation("com.github.aakira:napier:$napierVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                //  implementation ("io.ktor:ktor-client-json-jvm:1.0.0")
                //   implementation ("io.ktor:ktor-client-android:1.0.0")
                implementation("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

//        val iosMain by getting {
//            dependencies {
//                implementation("io.ktor:ktor-client-ios:$ktorVersion")
//                implementation("com.squareup.sqldelight:native-driver:1.5.3")
//            }
//        }
       // val iosTest by getting

//        val iosX64Main by getting {
//       dependsOn(iosMain)
//        }
        val iosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:1.5.3")
            }
        }
//        val iosSimulatorArm64Main by getting {
//            dependencies {
//               implementation("io.ktor:ktor-client-ios:$ktorVersion")
//                implementation("com.squareup.sqldelight:native-driver:1.5.3")
//            }
//        }
        val iosMain by creating {
            dependsOn(commonMain)
           // iosX64Main.dependsOn(this)
            iosX64Main.dependsOn(this)
          //  iosSimulatorArm64Main.dependsOn(this)
        }

        val iosX64Test by getting

        val iosTest by creating {
            dependsOn(commonTest)
           // iosX64Test.dependsOn(this)
            iosX64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
        }
//        val iosX64Test by getting{
//            dependsOn(iosTest)
//        }
//        val iosArm64Test by getting{
//            dependsOn(iosTest)
//        }
//        val iosSimulatorArm64Test by getting

    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}