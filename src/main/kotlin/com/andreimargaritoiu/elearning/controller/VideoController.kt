package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates
import com.andreimargaritoiu.elearning.service.VideoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("api/videos")
@CrossOrigin
class VideoController(private val videoService: VideoService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getVideos(@RequestParam uid: Optional<String>, @RequestParam playlistId: Optional<String>,
                  @RequestParam trending: Optional<Boolean>): Collection<Video> =
            videoService.getVideos(uid, playlistId, trending)

    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: String): Video = videoService.getVideo(videoId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addVideo(@RequestBody videoBuilder: VideoBuilder): Video = videoService.addVideo(videoBuilder)

    @PatchMapping("/{videoId}")
    fun updateVideo(@PathVariable videoId: String, @RequestBody videoUpdates: VideoUpdates): Video =
            videoService.updateVideo(videoId, videoUpdates)

    @DeleteMapping("/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVideo(@PathVariable videoId: String) = videoService.deleteVideo(videoId)

}