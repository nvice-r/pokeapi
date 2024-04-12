package com.example.r.nvice.playground.pokeapi.util.regex

object RegexUtil {

    fun generate(input: String): String {
        val filter = input.replace("[().-]".toRegex(), "")
        val inputArray = filter.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        if (input.isNotEmpty()) {
            for (anInputArray in inputArray) {
                builder.append("(?=.*\\b").append(anInputArray).append(")")
            }
            builder.append(".*$")
        } else {
            builder.append(".*")
        }
        return builder.toString()
    }
}