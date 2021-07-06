package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.repository.dataSource.InquiryDataSource

import org.springframework.stereotype.Service

@Service
class InquiryService(private val inquiryDataSource: InquiryDataSource) {

    fun addInquiry(inquiryBuilder: InquiryBuilder, inquirerEmail: String): Inquiry =
        inquiryDataSource.addInquiry(inquiryBuilder, inquirerEmail)

    fun updateInquiries(inquiries: List<String>) =
        inquiryDataSource.updateInquiries(inquiries)

    fun getInquiries(userId: String): Collection<Inquiry> {
        val inquiries: Collection<Inquiry> = inquiryDataSource.getInquiries()

        return inquiries.filter { it.mentorId == userId }
    }

}