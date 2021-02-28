package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.Video

interface VideoRepository {

    fun getVideos(): Collection<Video>

    fun getVideo(videoId: String): Video

    fun addVideo(video: Video): Video

    fun updateVideo(videoId: String, video: Video): Video

    fun deleteVideo(videoId: String)
    
}