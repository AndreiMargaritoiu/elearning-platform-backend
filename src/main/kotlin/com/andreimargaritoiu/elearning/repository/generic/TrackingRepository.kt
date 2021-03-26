package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.Tracking

interface TrackingRepository {

    fun getTrackings(): Collection<Tracking>
    fun addTracking(tracking: Tracking)
    
}