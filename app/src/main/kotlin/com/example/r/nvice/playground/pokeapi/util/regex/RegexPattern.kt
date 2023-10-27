package com.example.r.nvice.playground.pokeapi.util.regex

enum class RegexPattern(private val pattern: String) {
    NAME("^(?=\\w)(?!.*[\\s\\-\\.]{2})(?!.*[^\\w\\s\\-\\.])([\\w\\D]{0,99})(?:\\w)\$"),
    EMAIL("^(?=(((?=[^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\")).{2,64})@(?=(\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))).{0,255}$"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?!.*[^a-zA-Z0-9]).{6,20}$"),
    CARD_NO("^((?=8800)[0-9]{16})$"),
    MOBILE_NO("^((?=0[1-9])[0-9]{10})$")
    ;

    val regex: Regex
        get() = this.pattern.toRegex()
}