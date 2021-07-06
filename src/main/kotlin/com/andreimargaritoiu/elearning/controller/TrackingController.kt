package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.builders.TrackingBuilder
import com.andreimargaritoiu.elearning.model.models.Tracking
import com.andreimargaritoiu.elearning.service.TrackingService

import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("api/tracking")
class TrackingController(private val trackingService: TrackingService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping()
    fun getTrackings(@RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String): Collection<Tracking> {
        val userId: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        return trackingService.getTrackings(Optional.of(userId))
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTracking(
        @RequestBody vid: String,
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Tracking {
        val userId: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        val trackingBuilder = TrackingBuilder(vid, userId)
        return trackingService.addTracking(trackingBuilder)
    }

}