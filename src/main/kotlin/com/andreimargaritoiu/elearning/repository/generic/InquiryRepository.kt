package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.model.models.Inquiry

interface InquiryRepository {

    fun getInquiries(): Collection<Inquiry>
    fun addInquiry(inquiryBuilder: InquiryBuilder): Inquiry
    fun updateInquiries(inquiries: Collection<String>)

}