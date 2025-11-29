package com.armonia.backend.cycle.model

import java.time.LocalDate

data class FertileWindow(
    val start: LocalDate,
    val peak: LocalDate,
    val end: LocalDate
)
