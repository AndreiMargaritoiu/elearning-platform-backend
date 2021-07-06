package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.TrackingBuilder
import com.andreimargaritoiu.elearning.model.models.Tracking
import com.andreimargaritoiu.elearning.repository.dataSource.TrackingDataSource

import org.springframework.stereotype.Service
import java.util.*

@Service
class TrackingService(private val trackingDataSource: TrackingDataSource) {

    fun getTrackings(userId: Optional<String>): Collection<Tracking> {
        val trackings: Collection<Tracking> = trackingDataSource.getTrackings()

        if (!userId.isEmpty && userId.get().isNotEmpty())
        return trackings.filter {
            it.uid == userId.get()
        }
        return trackings
    }

    fun addTracking(trackingBuilder: TrackingBuilder): Tracking = trackingDataSource.addTracking(trackingBuilder)

}