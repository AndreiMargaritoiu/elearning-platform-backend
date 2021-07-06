package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.Workshop
import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.repository.dataSource.WorkshopDataSource

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class WorkshopService(private val workshopDataSource: WorkshopDataSource, private val userService: UserService) {

    fun addWorkshop(workshopBuilder: WorkshopBuilder, userId: String): Workshop {
        val isUserAdmin: Boolean = userService.getUser(userId).admin
        if (isUserAdmin) {
            return workshopDataSource.addWorkshop(workshopBuilder)
        }
        throw NoSuchElementException("User does not have permission to perform this action")
    }

    fun deleteWorkshop(workshopId: String) = workshopDataSource.deleteWorkshop(workshopId)

    fun getWorkshops(): Collection<Workshop> {
        return workshopDataSource.getWorkshops().filter { it.date >= Instant.now().toEpochMilli() }
    }

    fun registerToWorkshop(userEmail: String, workshopId: String): Workshop {
        workshopDataSource.registerToWorkshop(userEmail, workshopId)
        Thread.sleep(2000)
        return workshopDataSource.getWorkshopById(workshopId)
    }
}