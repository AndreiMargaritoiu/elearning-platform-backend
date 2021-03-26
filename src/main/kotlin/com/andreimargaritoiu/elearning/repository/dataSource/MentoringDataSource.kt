package com.andreimargaritoiu.elearning.repository.dataSource
import com.andreimargaritoiu.elearning.model.Mentorship
import com.andreimargaritoiu.elearning.repository.generic.MentoringRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
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
        val documentReference: DocumentReference = collectionReference.document(mentorshipId)
        val documentSnapshot: ApiFuture<DocumentSnapshot> = documentReference.get()

        return documentSnapshot.get().toObject(Mentorship::class.java)
                ?: throw NoSuchElementException("Could not find mentorship with id = $mentorshipId")
    }


    override fun addMentorship(mentorship: Mentorship): Mentorship {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == mentorship.id } == null)
            throw NoSuchElementException("Could not find mentorship with id = $mentorship.id")

        collectionReference.document(mentorship.id).set(mentorship)
        return mentorship
    }

    override fun updateMentorship(mentorshipId: String, mentorship: Mentorship): Mentorship {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == mentorshipId } == null)
            throw NoSuchElementException("Could not find Mentorship with id = $mentorshipId")

        collectionReference.document(mentorshipId).set(mentorship)
        return mentorship
    }

    override fun deleteMentorship(mentorshipId: String) {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == mentorshipId } == null)
            throw NoSuchElementException("Could not find mentorship with id = $mentorshipId")

        collectionReference.document(mentorshipId).delete()
    }
}