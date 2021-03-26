package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.Tracking
import com.andreimargaritoiu.elearning.repository.generic.TrackingRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class TrackingDataSource(firebaseInitialize: FirebaseInitialize): TrackingRepository {

    private final val collectionName = "tracking"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getTrackings(): Collection<Tracking> {
        val trackings = mutableListOf<Tracking>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            trackings.add(it.toObject(Tracking::class.java))
        }

        return trackings;
    }


    override fun addTracking(tracking: Tracking) {
        val trackings = getTrackings();
        trackings.forEach {
            if (it.uid == tracking.uid && it.vid == tracking.vid)
                throw IllegalArgumentException("Tracking already exists")
        }

        val ref: DocumentReference = collectionReference.document();
        ref.set(tracking)
    }
}