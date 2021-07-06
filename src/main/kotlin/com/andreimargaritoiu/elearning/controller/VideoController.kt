package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates
import com.andreimargaritoiu.elearning.service.VideoService

import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("api/videos")
class VideoController(private val videoService: VideoService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getVideos(
        @RequestParam uid: Optional<String>,
        @RequestParam playlistId: Optional<String>,
        @RequestParam trending: Optional<Boolean>,
        @RequestParam followers: Optional<Boolean>,
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Collection<Video> {
        val userId: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        return videoService.getVideos(uid, playlistId, trending, followers, userId)
    }


    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: String): Video = videoService.getVideo(videoId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addVideo(
        @RequestBody videoBuilder: VideoBuilder,
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String
    ): Video {
        val userId: String = FirebaseAuth.getInstance().verifyIdToken(authHeader).uid
        return videoService.addVideo(videoBuilder, userId)
    }

    @PatchMapping("/{videoId}")
    fun updateVideo(@PathVariable videoId: String, @RequestBody videoUpdates: VideoUpdates): Video =
        videoService.updateVideo(videoId, videoUpdates)

    @DeleteMapping("/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVideo(@PathVariable videoId: String) = videoService.deleteVideo(videoId)

}