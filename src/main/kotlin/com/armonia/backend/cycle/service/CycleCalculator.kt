package com.armonia.backend.cycle.service

import com.armonia.backend.cycle.model.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object CycleCalculator {
    fun predictedNextPeriodStart(p: CycleParams): LocalDate =
        p.lastPeriodStart.plusDays(p.avgCycleLength.toLong())

    fun predictedOvulationDay(p: CycleParams): LocalDate =
        p.lastPeriodStart.plusDays((p.avgCycleLength - p.lutealLength).toLong())

    fun ovulationDay(p: CycleParams): LocalDate = p.ovulationObserved() ?: predictedOvulationDay(p)

    fun fertileWindow(p: CycleParams): FertileWindow {
        val ov = ovulationDay(p)
        return FertileWindow(ov.minusDays(2), ov, ov.plusDays(1))
    }

    fun phaseForDate(p: CycleParams, date: LocalDate): Phase {
        val mensesEnd = p.lastPeriodStart.plusDays(p.mensesLength.toLong())
        return when {
            date >= p.lastPeriodStart && date < mensesEnd -> Phase.MENSTRUATION
            else -> Phase.UNKNOWN
        }
    }

    fun dayOfCycle(p: CycleParams, date: LocalDate): Int =
        ChronoUnit.DAYS.between(p.lastPeriodStart, date).toInt().coerceAtLeast(1)

    fun recalcAverages(periodStarts: List<LocalDate>): Pair<Int, Int?> {
        if (periodStarts.size < 2) return 28 to null
        val sorted = periodStarts.sorted()
        val diffs = sorted.zip(sorted.drop(1)).map { ChronoUnit.DAYS.between(it.first, it.second).toInt() }
        val avg = diffs.sum() / diffs.size
        return avg to null
    }
}
