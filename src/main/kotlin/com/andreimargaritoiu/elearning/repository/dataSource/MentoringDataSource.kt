package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.models.Mentorship
import com.andreimargaritoiu.elearning.model.builders.MentorshipBuilder
import com.andreimargaritoiu.elearning.model.updates.MentorshipUpdates
import com.andreimargaritoiu.elearning.repository.generic.MentoringRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.time.Instant
import kotlin.NoSuchElementException

@Repository
class MentoringDataSource(firebaseInitialize: FirebaseInitialize) : MentoringRepository {

    private final val collectionName = "mentoring"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getMentorships(): Collection<Mentorship> {
        val mentorships = mutableListOf<Mentorship>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            mentorships.add(it.toObject(Mentorship::class.java))
        }

        return mentorships;
    }

    override fun getMentorship(mentorshipId: String): Mentorship {
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(mentorshipId).get()

        return document.get().toObject(Mentorship::class.java)
                ?: throw NoSuchElementException("Could not find mentorship with id = $mentorshipId")
    }


    override fun addMentorship(mentorshipBuilder: MentorshipBuilder): Mentorship {
        val ref: DocumentReference = collectionReference.document()
        val mentorship = Mentorship(
                ref.id, mentorshipBuilder.description, mentorshipBuilder.mentorId,
                mentorshipBuilder.mentorEmail, mentorshipBuilder.price, Instant.now().toEpochMilli()
        )

        collectionReference.document(ref.id).set(mentorship)

        return mentorship
    }

    override fun updateMentorship(mentorshipId: String, mentorshipUpdates: MentorshipUpdates): Mentorship {
        val ref: DocumentReference = collectionReference.document(mentorshipId)
        val updates: MutableMap<String, Any> = mutableMapOf()
        if (mentorshipUpdates.description.isNotEmpty()) {
            updates["description"] = mentorshipUpdates.description
        }
        if (mentorshipUpdates.price.toString() != "0") {
            updates["price"] = mentorshipUpdates.price
        }

        ref.update(updates)

        return getMentorship(mentorshipId)
    }

    override fun deleteMentorship(mentorshipId: String) {
        val ref: DocumentReference = collectionReference.document(mentorshipId)
        getMentorship(mentorshipId)

        ref.delete()
    }
}