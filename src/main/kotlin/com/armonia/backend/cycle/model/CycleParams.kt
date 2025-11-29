package com.armonia.backend.cycle.model

import java.time.LocalDate

/**
 * Parámetros del ciclo usados para cálculos.
 */
data class CycleParams(
    val lastPeriodStart: LocalDate,
    val avgCycleLength: Int = 28,
    val lutealLength: Int = 14,
    val mensesLength: Int = 5,
    val ovulationPositiveOpkDate: LocalDate? = null,
    val bbtRiseDate: LocalDate? = null,
    val periodStarts: List<LocalDate> = emptyList()
) {
    init {
        require(avgCycleLength in 21..45) { "avgCycleLength fuera de rango" }
        require(lutealLength in 10..16) { "lutealLength fuera de rango" }
        require(mensesLength in 2..8) { "mensesLength fuera de rango" }
    }
    fun ovulationObserved(): LocalDate? = when {
        ovulationPositiveOpkDate != null -> ovulationPositiveOpkDate.plusDays(1)
        bbtRiseDate != null -> bbtRiseDate.minusDays(1)
        else -> null
    }
}
