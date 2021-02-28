package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.service.PlaylistService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("api/playlists")
class PlaylistController(private val playlistService: PlaylistService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getPlaylists(): Collection<Playlist> = playlistService.getPlaylists()

    @GetMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: String): Playlist = playlistService.getPlaylist(playlistId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addPlaylist(@RequestBody playlists: Playlist): Playlist = playlistService.addPlaylist(playlists)

    @PatchMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: String, @RequestBody playlists: Playlist): Playlist =
            playlistService.updatePlaylist(playlistId, playlists)

    @DeleteMapping("/{playlistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePlaylist(@PathVariable playlistId: String): Unit = playlistService.deletePlaylist(playlistId) // Unit = void

}