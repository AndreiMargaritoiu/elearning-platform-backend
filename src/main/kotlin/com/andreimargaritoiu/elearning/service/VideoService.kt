package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.repository.dataSource.VideoDataSource
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class VideoService(private val videoDataSource: VideoDataSource, private val playlistService: PlaylistService) {

    fun getVideo(videoId: String): Video = videoDataSource.getVideo(videoId)
    fun addVideo(Video: Video): Video = videoDataSource.addVideo(Video)
    fun updateVideo(videoId: String, Video: Video): Video = videoDataSource.updateVideo(videoId, Video)
    fun deleteVideo(videoId: String) = videoDataSource.deleteVideo(videoId)
    fun getVideos(uid: Optional<String>, playlistId: Optional<String>,
                           trending: Optional<Boolean>): Collection<Video> {
        val videos: Collection<Video>  = videoDataSource.getVideos()

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

        if (trending.get()) {
            return videos.filter { elem ->
                Instant.ofEpochMilli(elem.createdAt).plus(3, ChronoUnit.HOURS).isAfter(Instant.now());
            }
        }

        return videos;
    }
}