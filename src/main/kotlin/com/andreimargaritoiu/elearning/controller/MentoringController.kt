package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Mentorship
import com.andreimargaritoiu.elearning.model.builders.MentorshipBuilder
import com.andreimargaritoiu.elearning.model.updates.MentorshipUpdates
import com.andreimargaritoiu.elearning.service.MentoringService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

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
    fun getMentorships(): Collection<Mentorship> = mentoringService.getMentorships()

    @GetMapping("/{mentorshipId}")
    fun getMentorship(@PathVariable mentorshipId: String): Mentorship = mentoringService.getMentorship(mentorshipId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addMentorship(@RequestBody mentorshipBuilder: MentorshipBuilder): Mentorship =
            mentoringService.addMentorship(mentorshipBuilder)

    @PatchMapping("/{mentorshipId}")
    fun updateMentorship(@PathVariable mentorshipId: String, @RequestBody mentorshipUpdates: MentorshipUpdates):
            Mentorship = mentoringService.updateMentorship(mentorshipId, mentorshipUpdates)

    @DeleteMapping("/{mentorshipId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMentorship(@PathVariable mentorshipId: String) = mentoringService.deleteMentorship(mentorshipId)

}