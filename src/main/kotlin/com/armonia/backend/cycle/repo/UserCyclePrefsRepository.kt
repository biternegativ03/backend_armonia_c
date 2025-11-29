package com.armonia.backend.cycle.repo

import com.armonia.backend.cycle.model.UserCyclePrefsDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface UserCyclePrefsRepository : MongoRepository<UserCyclePrefsDocument, String>
