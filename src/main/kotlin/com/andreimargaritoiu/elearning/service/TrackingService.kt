package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.TrackingBuilder
import com.andreimargaritoiu.elearning.model.models.Tracking
import com.andreimargaritoiu.elearning.repository.dataSource.TrackingDataSource
import org.springframework.stereotype.Service

@Service
class TrackingService(private val trackingDataSource: TrackingDataSource) {

    fun getTrackings(userId: String): Collection<Tracking> {
        val trackings: Collection<Tracking> = trackingDataSource.getTrackings()
        return trackings.filter {
            it.uid == userId
        }
    }
    fun addTracking(trackingBuilder: TrackingBuilder): Tracking = trackingDataSource.addTracking(trackingBuilder)

}