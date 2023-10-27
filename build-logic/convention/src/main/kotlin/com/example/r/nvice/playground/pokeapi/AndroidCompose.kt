package com.example.r.nvice.playground.pokeapi

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("compose-compiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            "implementation"(platform(bom))
            "implementation"(libs.findLibrary(("compose-destination-core")).get())
            "ksp"(libs.findLibrary(("compose-destination-ksp")).get())
            "androidTestImplementation"(platform(bom))
        }
    }
}