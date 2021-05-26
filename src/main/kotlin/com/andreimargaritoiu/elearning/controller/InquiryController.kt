package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Inquiry
import com.andreimargaritoiu.elearning.model.builders.InquiryBuilder
import com.andreimargaritoiu.elearning.service.InquiryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import org.springframework.messaging.simp.SimpMessagingTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("api/inquiries")
class InquiryController(private val inquiryService: InquiryService) {

    val logger: Logger = LoggerFactory.getLogger("Inquiry logger")

    @Autowired
    private val messagingTemplate: SimpMessagingTemplate? = null

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/{userId}")
    fun getInquiries(
        @PathVariable userId: String
    ): Collection<Inquiry> = inquiryService.getInquiries(userId)

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun addInquiry(@RequestBody InquiryBuilder: InquiryBuilder): Inquiry =
//        inquiryService.addInquiry(InquiryBuilder)

    @MessageMapping("/ask-details")
    fun addInquiry(inquiryBuilder: InquiryBuilder) {
        logger.info("hello pussy")
        val newInquiry: Inquiry = inquiryService.addInquiry(inquiryBuilder)
        messagingTemplate?.convertAndSendToUser(inquiryBuilder.mentorId, "/inquiries/details", newInquiry)
    }

    @PatchMapping
    fun updateInquiry(@RequestBody inquiries: List<String>) = inquiryService.updateInquiries(inquiries)

}