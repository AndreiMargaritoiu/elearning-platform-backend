package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.models.Workshop
import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.repository.generic.WorkshopRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.*
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class WorkshopDataSource(firebaseInitialize: FirebaseInitialize) : WorkshopRepository {

    private final val collectionName = "workshops"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getWorkshops(): Collection<Workshop> {
        val workshops = mutableListOf<Workshop>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()

        querySnapshot.get().documents.forEach {
            workshops.add(it.toObject(Workshop::class.java))
        }

        return workshops;
    }

    override fun getWorkshopById(workshopId: String): Workshop {
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(workshopId).get()

        return document.get().toObject(Workshop::class.java)
            ?: throw NoSuchElementException("Could not find workshop with id = $workshopId")
    }

    override fun addWorkshop(workshopBuilder: WorkshopBuilder): Workshop {
        val ref: DocumentReference = collectionReference.document()
        val workshop = Workshop(
            ref.id,
            workshopBuilder.description,
            workshopBuilder.tag,
            workshopBuilder.location,
            workshopBuilder.thumbnailUrl,
            workshopBuilder.date,
            workshopBuilder.onlineEvent,
            emptyList(),
            workshopBuilder.capacity
        )

        collectionReference.document(ref.id).set(workshop)
        return workshop
    }

    override fun deleteWorkshop(workshopId: String) {
        val ref: DocumentReference = collectionReference.document(workshopId)
        getWorkshopById(workshopId)

        ref.delete()
    }

    override fun registerToWorkshop(userEmail: String, workshopId: String) {
        val ref: DocumentReference = collectionReference.document(workshopId)
        val workshop: Workshop = getWorkshopById(workshopId)
        val participants: List<String> = workshop.participants
        val newParticipants = mutableListOf<String>()

        participants.forEach { newParticipants.add(it) }

        if (newParticipants.contains(userEmail)) {
            newParticipants.remove(userEmail)
        } else if (newParticipants.size.toLong() != workshop.capacity) {
            newParticipants.add(userEmail)
        }

        val updates: MutableMap<String, Any> = mutableMapOf()
        updates["participants"] = newParticipants

        ref.update(updates)
    }
}