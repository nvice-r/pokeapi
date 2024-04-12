package com.example.r.nvice.playground.pokeapi

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    environment
}

@Suppress("EnumEntryName")
enum class AppFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    dev(FlavorDimension.environment, "dev"),
    prod(FlavorDimension.environment)
}

internal fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
    commonExtension.apply {
        FlavorDimension.values().forEach {
            flavorDimensions += it.name
        }
        productFlavors {
            AppFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        it.applicationIdSuffix?.let { suffix ->
                            applicationIdSuffix = suffix
                        }
                    }
                }
            }
        }
    }
}