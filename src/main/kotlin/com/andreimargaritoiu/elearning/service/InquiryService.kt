package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.repository.dataSource.InquiryDataSource

class InquiryService(private val inquiryDataSource: InquiryDataSource) {

    fun addInquiry(inquiryBuilder: InquiryBuilder): Inquiry =
        inquiryDataSource.addInquiry(inquiryBuilder)

    fun updateInquiries(inquiries: Collection<String>) =
        inquiryDataSource.updateInquiries(inquiries)

    fun getInquiries(userId: String): Collection<Inquiry> {
        val inquiries: Collection<Inquiry> = inquiryDataSource.getInquiries()

        return inquiries.filter { it.mentorId == userId }
    }

}