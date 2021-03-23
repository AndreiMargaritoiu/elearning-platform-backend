package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.repository.dataSource.VideoDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class VideoService(private val videoDataSource: VideoDataSource) {

    fun getVideos(uid: Optional<String>, playlistId: Optional<String>): Collection<Video> =
            videoDataSource.getVideos(uid, playlistId)
    fun getVideo(videoId: String): Video = videoDataSource.getVideo(videoId)
    fun addVideo(Video: Video): Video = videoDataSource.addVideo(Video)
    fun updateVideo(videoId: String, Video: Video): Video = videoDataSource.updateVideo(videoId, Video)
    fun deleteVideo(videoId: String) = videoDataSource.deleteVideo(videoId)
}