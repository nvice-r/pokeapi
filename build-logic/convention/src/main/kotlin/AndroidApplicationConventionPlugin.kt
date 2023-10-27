import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.example.r.nvice.playground.pokeapi.Configs.SDK_TARGET
import com.example.r.nvice.playground.pokeapi.android
import com.example.r.nvice.playground.pokeapi.configureFlavors
import com.example.r.nvice.playground.pokeapi.configureKotlinAndroid
import com.example.r.nvice.playground.pokeapi.configureLogger
import com.example.r.nvice.playground.pokeapi.id
import com.example.r.nvice.playground.pokeapi.kotlin
import com.example.r.nvice.playground.pokeapi.plugins

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            plugins {
                id("com.android.application")
                id("com.google.devtools.ksp")
                kotlin("android")
                kotlin("kapt")
            }
            android<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = SDK_TARGET
                configureFlavors(this)
                configureLogger(this)
            }
        }
    }
}