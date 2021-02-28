package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.repository.dataSource.PlaylistDataSource
import org.springframework.stereotype.Service

@Service
class PlaylistService (private val playlistDataSource: PlaylistDataSource) {

    fun getPlaylists(): Collection<Playlist> = playlistDataSource.getPlaylists()

    fun getPlaylist(PlaylistId: String): Playlist = playlistDataSource.getPlaylist(PlaylistId)

    fun addPlaylist(Playlist: Playlist): Playlist = playlistDataSource.addPlaylist(Playlist)

    fun updatePlaylist(PlaylistId: String, Playlist: Playlist): Playlist = playlistDataSource.updatePlaylist(PlaylistId, Playlist)

    fun deletePlaylist(PlaylistId: String) = playlistDataSource.deletePlaylist(PlaylistId)

}