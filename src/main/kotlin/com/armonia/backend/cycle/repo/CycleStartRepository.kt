package com.armonia.backend.cycle.repo

import com.armonia.backend.cycle.model.CycleStartDocument
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface CycleStartRepository : MongoRepository<CycleStartDocument, LocalDate> {
    fun findAllByOrderByDateDesc(): List<CycleStartDocument>
}
