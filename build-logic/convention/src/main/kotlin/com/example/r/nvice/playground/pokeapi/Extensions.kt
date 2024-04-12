package com.example.r.nvice.playground.pokeapi

import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure

inline fun Project.plugins(block: PluginManager.() -> Unit) {
    with(pluginManager, block)
}

inline fun <reified T : Any> Project.android(noinline block: T.() -> Unit) {
    extensions.configure(block)
}

fun PluginManager.id(id: String) = this.apply(id)
fun PluginManager.kotlin(module: String) = this.apply("org.jetbrains.kotlin.$module")