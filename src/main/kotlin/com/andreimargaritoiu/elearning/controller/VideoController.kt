package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.service.VideoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

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
    fun getVideos(): Collection<Video> = videoService.getVideos()

    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: String): Video = videoService.getVideo(videoId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addVideo(@RequestBody video: Video): Video = videoService.addVideo(video)

    @PatchMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: String, @RequestBody video: Video): Video =
            videoService.updateVideo(videoId, video)

    @DeleteMapping("/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVideo(@PathVariable videoId: String): Unit = videoService.deleteVideo(videoId) // Unit = void

}