package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.builders.PlaylistBuilder
import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates

interface PlaylistRepository {

    fun getPlaylists(): Collection<Playlist>
    fun getPlaylist(playlistId: String): Playlist
    fun addPlaylist(playlistBuilder: PlaylistBuilder, userId: String): Playlist
    fun updatePlaylist(playlistId: String, playlistUpdates: PlaylistUpdates)
    fun deletePlaylist(playlistId: String)
    
}