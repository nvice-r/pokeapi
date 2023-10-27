import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.example.r.nvice.playground.Configs.SDK_TARGET
import com.example.r.nvice.playground.android
import com.example.r.nvice.playground.configureFlavors
import com.example.r.nvice.playground.configureKotlinAndroid
import com.example.r.nvice.playground.configureLogger
import com.example.r.nvice.playground.id
import com.example.r.nvice.playground.kotlin
import com.example.r.nvice.playground.plugins

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