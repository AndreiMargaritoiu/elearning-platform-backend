package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.models.User
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.VideoDataSource

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class VideoService(
    private val videoDataSource: VideoDataSource,
    private val playlistService: PlaylistService,
    private val userService: UserService,
    private val trackingService: TrackingService
) {

    fun getVideo(videoId: String): Video = videoDataSource.getVideo(videoId)

    fun addVideo(videoBuilder: VideoBuilder, userId: String): Video = videoDataSource.addVideo(videoBuilder, userId)

    @Async
    fun updateVideo(videoId: String, videoUpdates: VideoUpdates): Video {
        videoDataSource.updateVideo(videoId, videoUpdates)
        Thread.sleep(2000)
        return getVideo(videoId)
    }

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

    fun getVideos(
        uid: Optional<String>, playlistId: Optional<String>,
        trending: Optional<Boolean>, followers: Optional<Boolean>, userId: String,
    ): Collection<Video> {
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
            val trendingMap = mutableMapOf<String, Int>()
            val recentVideos = mutableListOf<Video>()

            videos.filter { elem ->
                Instant.ofEpochMilli(elem.createdAt).plus(100, ChronoUnit.DAYS).isAfter(Instant.now())
            }.map {
                trendingMap.put(it.id, 0)
            }

            trackingService.getTrackings(Optional.empty()).map {
                if (trendingMap[it.vid] != null) {
                    trendingMap.replace(it.vid, trendingMap.getValue(it.vid), trendingMap.getValue(it.vid) + 1)
                }
            }

            trendingMap.toList()
                .sortedByDescending { (_, value) -> value }
                .take(10)
                .toMap()
                .map {
                    val currentVideo: Video? = videos.find { v -> v.id == it.key }
                    if (currentVideo != null) {
                        recentVideos.add(currentVideo)
                    }
                }

            return recentVideos
        }

        if (!followers.isEmpty && followers.get()) {
            val appUser: User = userService.getUser(userId)

            return videos.filter { appUser.following.contains(it.uid) }.sortedByDescending { it.createdAt }
        }

        return videos;
    }
}