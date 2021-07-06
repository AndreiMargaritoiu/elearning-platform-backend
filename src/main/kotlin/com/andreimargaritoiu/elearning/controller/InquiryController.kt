package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.service.InquiryService

import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("api/inquiries")
class InquiryController(private val inquiryService: InquiryService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping()
    fun getInquiries(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Collection<Inquiry> {
        val userId: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        return inquiryService.getInquiries(userId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInquiry(
        @RequestBody inquiryBuilder: InquiryBuilder,
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Inquiry {
        val inquirerEmail: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).email
        return inquiryService.addInquiry(inquiryBuilder, inquirerEmail)
    }

    @PatchMapping
    fun updateInquiry(@RequestBody inquiries: List<String>) = inquiryService.updateInquiries(inquiries)

}