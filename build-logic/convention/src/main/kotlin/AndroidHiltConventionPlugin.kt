import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.example.r.nvice.playground.pokeapi.plugins

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply("dagger.hilt.android.plugin")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                "implementation"(libs.findLibrary("hilt-android").get())
                "kapt"(libs.findLibrary("hilt-android-compiler").get())
//                "testImplementation"(libs.findLibrary("hilt.android.testing").get())
            }
        }
    }
}