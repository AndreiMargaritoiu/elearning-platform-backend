package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.VideoDataSource
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class VideoService(private val videoDataSource: VideoDataSource, private val playlistService: PlaylistService) {

    fun getVideo(videoId: String): Video = videoDataSource.getVideo(videoId)
    fun addVideo(videoBuilder: VideoBuilder): Video = videoDataSource.addVideo(videoBuilder)
    fun updateVideo(videoId: String, videoUpdates: VideoUpdates): Video =
            videoDataSource.updateVideo(videoId, videoUpdates)
    fun deleteVideo(videoId: String) {
        videoDataSource.deleteVideo(videoId)
        val playlists: Collection<Playlist> =
                playlistService.getPlaylists(category = Optional.empty(), uid = Optional.empty())
        playlists.forEach {
            if (it.videoRefs.contains(videoId)) {
                val updates = PlaylistUpdates(videoRefs = it.videoRefs.filter { itt -> itt != videoId })
                playlistService.updatePlaylist(it.id, updates)
            }
        }
    }

    fun getVideos(uid: Optional<String>, playlistId: Optional<String>,
                  trending: Optional<Boolean>): Collection<Video> {
        val videos: Collection<Video> = videoDataSource.getVideos()

        if (!uid.isEmpty) {
            return videos.filter { it.uid == uid.get() }
        }

        if (!playlistId.isEmpty) {
            try {
                val playlist: Playlist = playlistService.getPlaylist(playlistId.get())

                return videos.filter { elem -> playlist.videoRefs.contains(elem.id) }
            } catch (e: Exception) {
                throw NoSuchElementException("Could not find playlist with id = ${playlistId.get()}")
            }
        }

        if (!trending.isEmpty && trending.get()) {
            return videos.filter { elem ->
                Instant.ofEpochMilli(elem.createdAt).plus(100, ChronoUnit.DAYS).isAfter(Instant.now());
            }
        }

        return videos;
    }
}