plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")

    // Custom plugin to generate the native libs and bindings file
    id("org.rgbtools.plugins.generate-android-bindings")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(file("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("net.java.dev.jna:jna:5.8.0@aar")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    api("org.slf4j:slf4j-api:1.7.30")

    androidTestImplementation("com.github.tony19:logback-android:2.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "org.rgbtools"
                artifactId = "rgb-lib-android"
                version = "0.1.3"
                from(components["release"])
                pom {
                    name.set("rgb-lib-android")
                    description.set("RGB Lib Kotlin language bindings.")
                    url.set("https://github.com/RGB-Tools/rgb-lib-kotlin")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://github.com/RGB-Tools/rgb-lib-kotlin/blob/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            id.set("zoedberg")
                            name.set("Zoe Faltibà")
                            email.set("zoefaltiba@gmail.com")
                        }
                        developer {
                            id.set("nicbus")
                            name.set("Nicola Busanello")
                            email.set("nicola.busanello@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/RGB-Tools/rgb-lib-kotlin.git")
                        developerConnection.set("scm:git:ssh://github.com/RGB-Tools/rgb-lib-kotlin.git")
                        url.set("https://github.com/RGB-Tools/rgb-lib-kotlin")
                    }
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
