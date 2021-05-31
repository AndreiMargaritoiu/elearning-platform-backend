package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.Workshop
import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.repository.dataSource.WorkshopDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class WorkshopService(private val workshopDataSource: WorkshopDataSource, private val userService: UserService) {

    fun addWorkshop(workshopBuilder: WorkshopBuilder, userId: String): Workshop {
        val isUserAdmin: Boolean = userService.getUser(userId).admin == true
        if (isUserAdmin) {
            return workshopDataSource.addWorkshop(workshopBuilder)
        }
        throw NoSuchElementException("User does not have permission to perform this action")
    }
    fun deleteWorkshop(workshopId: String) = workshopDataSource.deleteWorkshop(workshopId)
    fun getWorkshops(): Collection<Workshop> = workshopDataSource.getWorkshops()
    fun registerToWorkshop(userEmail: String, workshopId: String): Workshop =
        workshopDataSource.registerToWorkshop(userEmail, workshopId)
}