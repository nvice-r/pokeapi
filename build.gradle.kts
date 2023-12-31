// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.io.gitlab.arturbosch.detekt) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
//    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<DetektExtension> {
        buildUponDefaultConfig = true
        allRules = false
        config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
        baseline = file("$projectDir/config/detekt/baseline.xml")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

true // Needed to make the Suppress annotation work for the plugins block