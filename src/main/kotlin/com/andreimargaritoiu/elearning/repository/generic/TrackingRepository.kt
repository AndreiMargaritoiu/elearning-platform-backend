package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.builders.TrackingBuilder
import com.andreimargaritoiu.elearning.model.models.Tracking

interface TrackingRepository {

    fun getTrackings(): Collection<Tracking>
    fun addTracking(trackingBuilder: TrackingBuilder): Tracking
    
}