package com.blakdragon.petscapeclan.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.toString(format: String): String {
    return format(DateTimeFormatter.ofPattern(format))
}
