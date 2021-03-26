package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.repository.dataSource.PlaylistDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlaylistService(private val playlistDataSource: PlaylistDataSource) {

    fun getPlaylist(playlistId: String): Playlist = playlistDataSource.getPlaylist(playlistId)
    fun addPlaylist(playlist: Playlist): Playlist = playlistDataSource.addPlaylist(playlist)
    fun updatePlaylist(playlistId: String, Playlist: Playlist): Playlist = playlistDataSource.updatePlaylist(playlistId, Playlist)
    fun deletePlaylist(playlistId: String) = playlistDataSource.deletePlaylist(playlistId)
    fun getPlaylists(category: Optional<String>, uid: Optional<String>): Collection<Playlist> {
        val playlists: Collection<Playlist>  = playlistDataSource.getPlaylists()

        if (!category.isEmpty) {
            return playlists.filter { it.category == category.get() }
        }

        if (!uid.isEmpty) {
            return playlists.filter { it.uid == uid.get() }
        }

        return playlists;
    }
}