package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.Video
import java.util.*

interface VideoRepository {

    fun getVideos(uid: Optional<String>, playlistId: Optional<String>): Collection<Video>
    fun getVideo(videoId: String): Video
    fun addVideo(video: Video): Video
    fun updateVideo(videoId: String, video: Video): Video
    fun deleteVideo(videoId: String)

}