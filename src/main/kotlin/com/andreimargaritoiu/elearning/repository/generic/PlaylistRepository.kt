package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.Playlist
import java.util.*

interface PlaylistRepository {

    fun getPlaylists(category: Optional<String>, uid: Optional<String>): Collection<Playlist>
    fun getPlaylist(playlistId: String): Playlist
    fun addPlaylist(playlist: Playlist): Playlist
    fun updatePlaylist(playlistId: String, playlist: Playlist): Playlist
    fun deletePlaylist(playlistId: String)
    
}