package com.armonia.backend.cycle.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "cycle_starts")
data class CycleStartDocument(
    @Id val date: LocalDate
)
