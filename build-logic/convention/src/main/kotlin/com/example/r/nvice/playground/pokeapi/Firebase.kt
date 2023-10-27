package com.example.r.nvice.playground.pokeapi

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureFirebase(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            val bom = libs.findLibrary("firebase-bom").get()
            "implementation"(platform(bom))
        }
    }
}
