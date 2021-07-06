package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Mentorship
import com.andreimargaritoiu.elearning.model.builders.MentorshipBuilder
import com.andreimargaritoiu.elearning.model.updates.MentorshipUpdates
import com.andreimargaritoiu.elearning.service.MentoringService

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.HttpHeaders

@RestController
@RequestMapping("api/mentoring")
class MentoringController(private val mentoringService: MentoringService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getMentorships(
        @RequestParam uid: Optional<String>,
        @RequestParam category: Optional<Collection<String>>,
    ): Collection<Mentorship> =
        mentoringService.getMentorships(uid, category)


    @GetMapping("/{mentorshipId}")
    fun getMentorship(@PathVariable mentorshipId: String): Mentorship {
        return mentoringService.getMentorship(mentorshipId)
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addMentorship(
        @RequestBody mentorshipBuilder: MentorshipBuilder,
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Mentorship {
        val userId = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        return mentoringService.addMentorship(mentorshipBuilder, userId)
    }

    @PatchMapping("/{mentorshipId}")
    fun updateMentorship(@PathVariable mentorshipId: String, @RequestBody mentorshipUpdates: MentorshipUpdates):
            Mentorship = mentoringService.updateMentorship(mentorshipId, mentorshipUpdates)

    @DeleteMapping("/{mentorshipId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMentorship(@PathVariable mentorshipId: String) = mentoringService.deleteMentorship(mentorshipId)

}