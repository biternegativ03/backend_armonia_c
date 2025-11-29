package com.armonia.backend.cycle

import com.armonia.backend.cycle.model.CycleParams
import com.armonia.backend.cycle.service.CycleCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CycleCalculatorTests {
    @Test
    fun `next period prediction`() {
        val p = CycleParams(lastPeriodStart = LocalDate.of(2025,11,1), avgCycleLength = 28)
        assertEquals(LocalDate.of(2025,11,29), CycleCalculator.predictedNextPeriodStart(p))
    }

    @Test
    fun `ovulation prediction`() {
        val p = CycleParams(lastPeriodStart = LocalDate.of(2025,11,1), avgCycleLength = 28, lutealLength = 14)
        assertEquals(LocalDate.of(2025,11,15), CycleCalculator.predictedOvulationDay(p))
    }
}
