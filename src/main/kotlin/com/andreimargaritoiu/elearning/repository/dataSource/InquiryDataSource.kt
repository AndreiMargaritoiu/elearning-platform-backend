package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.repository.generic.InquiryRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class InquiryDataSource(firebaseInitialize: FirebaseInitialize): InquiryRepository {

    private final val collectionName = "inquiry"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getInquiries(): Collection<Inquiry> {
        val inquiries = mutableListOf<Inquiry>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            inquiries.add(it.toObject(Inquiry::class.java))
        }

        return inquiries
    }


    override fun addInquiry(inquiryBuilder: InquiryBuilder): Inquiry {
        val ref: DocumentReference = collectionReference.document()
        val inquiry = Inquiry(
            ref.id,
            inquiryBuilder.mentorId,
            inquiryBuilder.inquirerEmail,
            false,
            Instant.now().toEpochMilli()
        )

        collectionReference.document(ref.id).set(inquiry)

        return inquiry
    }

    override fun updateInquiries(inquiries: Collection<String>) {
        // TODO check if there is no inexistant inquiry
//        val appInquiries = getInquiries();
//        appInquiries.forEach {
//            inquiries.firstOrNull {
//                it = appInquiries.
//                })
//                throw IllegalArgumentException("Inquiry already exists")
//        }

        inquiries.forEach {
            val ref: DocumentReference = collectionReference.document(it)
            val updates: MutableMap<String, Any> = mutableMapOf()
            updates["isRead"] = true

            ref.update(updates)
        }
    }
}