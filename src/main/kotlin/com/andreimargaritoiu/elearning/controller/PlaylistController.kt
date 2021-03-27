package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates
import com.andreimargaritoiu.elearning.service.PlaylistService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.NoSuchElementException

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
    fun getPlaylists(@RequestParam category: Optional<String>, @RequestParam uid: Optional<String>):
            Collection<Playlist> = playlistService.getPlaylists(category, uid)

    @GetMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: String): Playlist = playlistService.getPlaylist(playlistId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addPlaylist(@RequestBody playlist: Playlist): Playlist = playlistService.addPlaylist(playlist)

    @PatchMapping("/{playlistId}")
    fun updatePlaylist(@PathVariable playlistId: String, @RequestBody playlistsUpdates: PlaylistUpdates): Playlist =
            playlistService.updatePlaylist(playlistId, playlistsUpdates)

    @DeleteMapping("/{playlistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePlaylist(@PathVariable playlistId: String) = playlistService.deletePlaylist(playlistId)

}