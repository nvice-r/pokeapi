package com.example.r.nvice.playground.pokeapi

import com.example.r.nvice.playground.pokeapi.Configs.JAVA_VERSION
import com.example.r.nvice.playground.pokeapi.Configs.SDK_COMPILE
import com.example.r.nvice.playground.pokeapi.Configs.SDK_MIN
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = SDK_COMPILE

        defaultConfig {
            minSdk = SDK_MIN
        }

        buildFeatures {
            viewBinding = true
        }

        compileOptions {
            sourceCompatibility = JAVA_VERSION
            targetCompatibility = JAVA_VERSION
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
                "-opt-in=kotlin.Experimental",
                "-opt-in=kotlin.ExperimentalPermissionsApi",
                "-opt-in=kotlin.ExperimentalStdlibApi",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview"
            )

            jvmTarget = JAVA_VERSION.toString()
        }

        lint {
            checkReleaseBuilds = false
        }

        sourceSets {
            getByName("androidTest") {
                java.srcDirs("src/androidTest/java", "src/androidTest/kotlin")
                kotlin.srcDirs("src/androidTest/java", "src/androidTest/kotlin")
            }
            getByName("main") {
                java.srcDirs("src/main/java", "src/main/kotlin")
                kotlin.srcDirs("src/main/java", "src/main/kotlin")
            }
            getByName("test") {
                java.srcDirs("src/test/java", "src/test/kotlin")
                kotlin.srcDirs("src/test/java", "src/test/kotlin")
            }
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
