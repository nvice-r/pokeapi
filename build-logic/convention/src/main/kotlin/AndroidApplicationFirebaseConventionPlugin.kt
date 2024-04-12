import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import com.example.r.nvice.playground.pokeapi.configureFirebase
import com.example.r.nvice.playground.pokeapi.plugins

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<ApplicationExtension>()
            configureFirebase(extension)
            plugins {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }
        }
    }
}