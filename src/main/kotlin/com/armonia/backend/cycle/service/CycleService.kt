package com.armonia.backend.cycle.service

import com.armonia.backend.cycle.model.*
import com.armonia.backend.cycle.repo.CycleStartRepository
import com.armonia.backend.cycle.repo.UserCyclePrefsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CycleService(
    private val startRepo: CycleStartRepository,
    private val prefsRepo: UserCyclePrefsRepository
) {
    fun listStarts(): List<LocalDate> = startRepo.findAllByOrderByDateDesc().map { it.date }

    fun getPrefs(): UserCyclePrefsDocument? = prefsRepo.findById("singleton").orElse(null)

    @Transactional
    fun registerNewPeriod(start: LocalDate) {
        startRepo.save(CycleStartDocument(start))
        val all = listStarts()
        val existing = getPrefs()
        val (avg, _) = CycleCalculator.recalcAverages(all)
        val updated = (existing ?: UserCyclePrefsDocument()).copy(
            lastPeriodStart = start,
            avgCycleLength = avg
        )
        prefsRepo.save(updated)
    }

    @Transactional
    fun setObservedOvulation(opkDate: LocalDate?, bbtRise: LocalDate?) {
        val prefs = getPrefs() ?: UserCyclePrefsDocument()
        val updated = prefs.copy(
            ovulationPositiveOpkDate = opkDate ?: prefs.ovulationPositiveOpkDate,
            bbtRiseDate = bbtRise ?: prefs.bbtRiseDate
        )
        prefsRepo.save(updated)
    }

    fun buildParams(): CycleParams? {
        val prefs = getPrefs() ?: return null
        val lastStart = prefs.lastPeriodStart ?: return null
        val starts = listStarts()
        return CycleParams(
            lastPeriodStart = lastStart,
            avgCycleLength = prefs.avgCycleLength,
            lutealLength = prefs.lutealLength,
            mensesLength = prefs.mensesLength,
            ovulationPositiveOpkDate = prefs.ovulationPositiveOpkDate,
            bbtRiseDate = prefs.bbtRiseDate,
            periodStarts = starts
        )
    }

    fun nextPeriod(): LocalDate? = buildParams()?.let { CycleCalculator.predictedNextPeriodStart(it) }

    fun ovulationDay(): LocalDate? = buildParams()?.let { CycleCalculator.ovulationDay(it) }

    fun fertileWindow(): FertileWindow? = buildParams()?.let { CycleCalculator.fertileWindow(it) }

    fun dayOfCycle(date: LocalDate): Int? = buildParams()?.let { CycleCalculator.dayOfCycle(it, date) }
}
