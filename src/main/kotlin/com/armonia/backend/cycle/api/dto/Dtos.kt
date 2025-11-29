package com.armonia.backend.cycle.api.dto

import com.armonia.backend.cycle.model.FertileWindow
import com.armonia.backend.cycle.model.UserCyclePrefsDocument
import java.time.LocalDate
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

// Requests

data class PeriodStartRequest(@field:NotNull val date: LocalDate)

data class OvulationObservationRequest(
    val opkDate: LocalDate? = null,
    val bbtRiseDate: LocalDate? = null
)

data class UpdatePrefsRequest(
    val lastPeriodStart: LocalDate? = null,
    @field:Min(21) @field:Max(45) val avgCycleLength: Int? = null,
    @field:Min(10) @field:Max(16) val lutealLength: Int? = null,
    @field:Min(2) @field:Max(8) val mensesLength: Int? = null
)

// Responses

data class CycleStartDto(val date: LocalDate)

data class CyclePredictionResponse(val date: LocalDate)

data class FertileWindowResponse(val start: LocalDate, val peak: LocalDate, val end: LocalDate)

data class DayOfCycleResponse(val day: Int)

data class UserPrefsResponse(
    val lastPeriodStart: LocalDate?,
    val avgCycleLength: Int,
    val lutealLength: Int,
    val mensesLength: Int,
    val ovulationPositiveOpkDate: LocalDate?,
    val bbtRiseDate: LocalDate?
)

fun FertileWindow.toResponse() = FertileWindowResponse(start, peak, end)
fun UserCyclePrefsDocument.toResponse() = UserPrefsResponse(
    lastPeriodStart, avgCycleLength, lutealLength, mensesLength, ovulationPositiveOpkDate, bbtRiseDate
)
