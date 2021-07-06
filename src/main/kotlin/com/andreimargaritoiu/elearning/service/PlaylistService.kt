package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.PlaylistBuilder
import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.PlaylistDataSource

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlaylistService(private val playlistDataSource: PlaylistDataSource) {

    fun getPlaylist(playlistId: String): Playlist = playlistDataSource.getPlaylist(playlistId)

    fun addPlaylist(playlistBuilder: PlaylistBuilder, userId: String): Playlist =
        playlistDataSource.addPlaylist(playlistBuilder, userId)

    fun deletePlaylist(playlistId: String) = playlistDataSource.deletePlaylist(playlistId)

    @Async
    fun updatePlaylist(playlistId: String, playlistsUpdates: PlaylistUpdates): Playlist {
        playlistDataSource.updatePlaylist(playlistId, playlistsUpdates)
        Thread.sleep(2000)
        return getPlaylist(playlistId)
    }

    fun getPlaylists(category: Optional<String>, uid: Optional<String>): Collection<Playlist> {
        val playlists: Collection<Playlist> = playlistDataSource.getPlaylists()

        if (!category.isEmpty) {
            return playlists.filter { it.category == category.get() }
        }

        if (!uid.isEmpty) {
            return playlists.filter { it.uid == uid.get() }
        }

        return playlists;
    }
}