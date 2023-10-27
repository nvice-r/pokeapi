package com.example.r.nvice.playground.pokeapi.util.extension

import com.example.r.nvice.playground.pokeapi.util.regex.RegexUtil
import java.util.Locale

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
}

fun String.toGenerativeRegex() = RegexUtil.generate(this).toRegex()