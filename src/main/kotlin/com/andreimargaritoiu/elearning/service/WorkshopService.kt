package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.Workshop
import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.repository.dataSource.WorkshopDataSource
import org.springframework.stereotype.Service

@Service
class WorkshopService(private val workshopDataSource: WorkshopDataSource) {

    fun addWorkshop(workshopBuilder: WorkshopBuilder): Workshop =
        workshopDataSource.addWorkshop(workshopBuilder)

    fun deleteWorkshop(workshopId: String) = workshopDataSource.deleteWorkshop(workshopId)
    fun getWorkshops(): Collection<Workshop> = workshopDataSource.getWorkshops()
    fun registerToWorkshop(userEmail: String, workshopId: String): Workshop =
        workshopDataSource.registerToWorkshop(userEmail, workshopId)
}