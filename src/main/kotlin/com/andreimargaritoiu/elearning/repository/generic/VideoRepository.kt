package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates

interface VideoRepository {

    fun getVideos(): Collection<Video>
    fun getVideo(videoId: String): Video
    fun addVideo(videoBuilder: VideoBuilder, userId: String): Video
    fun updateVideo(videoId: String, videoUpdates: VideoUpdates): Video
    fun deleteVideo(videoId: String)

}