package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.model.models.Workshop

interface WorkshopRepository {

    fun getWorkshops(): Collection<Workshop>
    fun getWorkshopById(workshopId: String): Workshop
    fun addWorkshop(workshopBuilder: WorkshopBuilder): Workshop
    fun deleteWorkshop(workshopId: String)
    fun registerToWorkshop(userEmail: String, workshopId: String): Workshop

}