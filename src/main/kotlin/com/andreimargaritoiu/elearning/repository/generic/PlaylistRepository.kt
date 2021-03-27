package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates

interface PlaylistRepository {

    fun getPlaylists(): Collection<Playlist>
    fun getPlaylist(playlistId: String): Playlist
    fun addPlaylist(playlist: Playlist): Playlist
    fun updatePlaylist(playlistId: String, playlistUpdates: PlaylistUpdates): Playlist
    fun deletePlaylist(playlistId: String)
    
}