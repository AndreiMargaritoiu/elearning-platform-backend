package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.repository.generic.VideoRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class VideoDataSource(firebaseInitialize: FirebaseInitialize): VideoRepository {

    val collectionName = "Videos"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getVideos(): Collection<Video> {
        val videos = mutableListOf<Video>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            videos.add(it.toObject(Video::class.java))
        }

        return videos;
    }

    override fun getVideo(videoId: String): Video {
        val documentReference: DocumentReference = collectionReference.document(videoId)
        val documentSnapshot: ApiFuture<DocumentSnapshot> = documentReference.get()

        return documentSnapshot.get().toObject(Video::class.java)
                ?: throw NoSuchElementException("Could not find video with id = $videoId")
    }

    override fun addVideo(video: Video): Video {
        val videos = mutableListOf<Video>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            videos.add(it.toObject(Video::class.java))
        }
        videos.forEach {
            if (it.videoUrl == video.videoUrl)
                throw IllegalArgumentException("Video with videoUrl = ${video.videoUrl} already exists")
        }

        collectionReference.document().set(video)
        return video
    }

    override fun updateVideo(videoId: String, video: Video): Video {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == videoId } == null)
            throw NoSuchElementException("Could not find video with id = $videoId")

        collectionReference.document(videoId).set(video)
        return video
    }

    override fun deleteVideo(videoId: String) {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == videoId } == null)
            throw NoSuchElementException("Could not find video with id = $videoId")

        collectionReference.document(videoId).delete()
    }
}