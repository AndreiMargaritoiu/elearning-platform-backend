package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.repository.generic.InquiryRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
class InquiryDataSource(firebaseInitialize: FirebaseInitialize): InquiryRepository {

    private final val collectionName = "inquiries"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getInquiries(): Collection<Inquiry> {
        val inquiries = mutableListOf<Inquiry>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            inquiries.add(it.toObject(Inquiry::class.java))
        }

        return inquiries
    }

    fun getInquiry(inquiryId: String): Inquiry {
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(inquiryId).get()

        return document.get().toObject(Inquiry::class.java)
            ?: throw NoSuchElementException("Could not find inquiry with id = $inquiryId")
    }

    override fun addInquiry(inquiryBuilder: InquiryBuilder, inquirerEmail: String): Inquiry {
        val ref: DocumentReference = collectionReference.document()
        val inquiry = Inquiry(
            ref.id,
            inquiryBuilder.mentorId,
            inquirerEmail,
            false,
            Instant.now().toEpochMilli()
        )

        collectionReference.document(ref.id).set(inquiry)

        return inquiry
    }

    override fun updateInquiries(inquiries: List<String>) {
        // TODO check if there is no inexistant inquiry
        inquiries.forEach {
            val ref: DocumentReference = collectionReference.document(it)
            val updates: MutableMap<String, Any> = mutableMapOf()
            updates["read"] = true

            ref.update(updates)
        }
    }
}