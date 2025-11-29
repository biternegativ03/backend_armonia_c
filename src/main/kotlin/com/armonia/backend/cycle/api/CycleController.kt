package com.armonia.backend.cycle.api

import com.armonia.backend.cycle.api.dto.*
import com.armonia.backend.cycle.model.UserCyclePrefsDocument
import com.armonia.backend.cycle.repo.UserCyclePrefsRepository
import com.armonia.backend.cycle.service.CycleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api")
@Validated
class CycleController(
    private val service: CycleService,
    private val prefsRepo: UserCyclePrefsRepository
) {
    @GetMapping("/cycles")
    fun listCycles(): List<CycleStartDto> = service.listStarts().map { CycleStartDto(it) }

    @PostMapping("/cycles")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody req: PeriodStartRequest) {
        service.registerNewPeriod(req.date)
    }

    @GetMapping("/cycles/next-period")
    fun nextPeriod(): ResponseEntity<CyclePredictionResponse> = service.nextPeriod()?.let { ResponseEntity.ok(CyclePredictionResponse(it)) } ?: ResponseEntity.noContent().build()

    @GetMapping("/cycles/ovulation")
    fun ovulation(): ResponseEntity<CyclePredictionResponse> = service.ovulationDay()?.let { ResponseEntity.ok(CyclePredictionResponse(it)) } ?: ResponseEntity.noContent().build()

    @GetMapping("/cycles/fertile-window")
    fun fertileWindow(): ResponseEntity<FertileWindowResponse> = service.fertileWindow()?.let { ResponseEntity.ok(it.toResponse()) } ?: ResponseEntity.noContent().build()

    @GetMapping("/cycles/day-of-cycle")
    fun dayOfCycle(@RequestParam("date") date: String): ResponseEntity<DayOfCycleResponse> =
        service.dayOfCycle(LocalDate.parse(date))?.let { ResponseEntity.ok(DayOfCycleResponse(it)) } ?: ResponseEntity.noContent().build()

    @GetMapping("/prefs")
    fun getPrefs(): ResponseEntity<UserPrefsResponse> = service.getPrefs()?.let { ResponseEntity.ok(it.toResponse()) } ?: ResponseEntity.noContent().build()

    @PutMapping("/prefs")
    fun updatePrefs(@RequestBody req: UpdatePrefsRequest): UserPrefsResponse {
        val existing = service.getPrefs() ?: UserCyclePrefsDocument()
        val updated = existing.copy(
            lastPeriodStart = req.lastPeriodStart ?: existing.lastPeriodStart,
            avgCycleLength = req.avgCycleLength ?: existing.avgCycleLength,
            lutealLength = req.lutealLength ?: existing.lutealLength,
            mensesLength = req.mensesLength ?: existing.mensesLength
        )
        prefsRepo.save(updated)
        return updated.toResponse()
    }

    @PostMapping("/ovulation-observed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setObserved(@RequestBody req: OvulationObservationRequest) {
        service.setObservedOvulation(req.opkDate, req.bbtRiseDate)
    }
}
