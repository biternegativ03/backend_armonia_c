package com.armonia.backend.cycle.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "user_cycle_prefs")
data class UserCyclePrefsDocument(
    @Id val id: String = "singleton",
    val lastPeriodStart: LocalDate? = null,
    val avgCycleLength: Int = 28,
    val lutealLength: Int = 14,
    val mensesLength: Int = 5,
    val ovulationPositiveOpkDate: LocalDate? = null,
    val bbtRiseDate: LocalDate? = null
)
