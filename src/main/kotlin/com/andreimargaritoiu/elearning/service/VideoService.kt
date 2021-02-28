package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.repository.dataSource.VideoDataSource
import org.springframework.stereotype.Service

@Service
class VideoService(private val videoDataSource: VideoDataSource) {

    fun getVideos(): Collection<Video> = videoDataSource.getVideos()

    fun getVideo(VideoId: String): Video = videoDataSource.getVideo(VideoId)

    fun addVideo(Video: Video): Video = videoDataSource.addVideo(Video)

    fun updateVideo(VideoId: String, Video: Video): Video = videoDataSource.updateVideo(VideoId, Video)

    fun deleteVideo(VideoId: String) = videoDataSource.deleteVideo(VideoId)
}