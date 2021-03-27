package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.Tracking
import com.andreimargaritoiu.elearning.repository.dataSource.TrackingDataSource
import org.springframework.stereotype.Service

@Service
class TrackingService(private val trackingDataSource: TrackingDataSource) {

    fun getTrackings(): Collection<Tracking> = trackingDataSource.getTrackings()
    fun addTracking(tracking: Tracking) = trackingDataSource.addTracking(tracking)

}