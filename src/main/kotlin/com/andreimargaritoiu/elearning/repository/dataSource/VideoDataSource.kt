package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.builders.VideoBuilder
import com.andreimargaritoiu.elearning.model.models.Video
import com.andreimargaritoiu.elearning.model.updates.VideoUpdates
import com.andreimargaritoiu.elearning.repository.generic.VideoRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture

@Repository
class VideoDataSource(firebaseInitialize: FirebaseInitialize) : VideoRepository {

    private final val collectionName = "videos"
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
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(videoId).get()

        return document.get().toObject(Video::class.java)
                ?: throw NoSuchElementException("Could not find video with id = $videoId")
    }


    override fun addVideo(videoBuilder: VideoBuilder): Video {
//        val ref: DocumentReference = collectionReference.document()
        val video = Video(
            videoBuilder.id, videoBuilder.uid, videoBuilder.videoUrl, videoBuilder.thumbnailUrl,
            videoBuilder.title, videoBuilder.description, Instant.now().toEpochMilli(), videoBuilder.searchIndex
        )

        collectionReference.document(video.id).set(video)
        return video

//        val ref: DocumentReference = collectionReference.document()
//        val mentorship = Mentorship(
//            ref.id, mentorshipBuilder.description, mentorshipBuilder.mentorId,
//            mentorshipBuilder.mentorEmail, mentorshipBuilder.price, Instant.now().toEpochMilli()
//        )
//
//        collectionReference.document(ref.id).set(mentorship)
//
//        return mentorship
    }

    override fun updateVideo(videoId: String, videoUpdates: VideoUpdates): Video {
        val ref: DocumentReference = collectionReference.document(videoId)
        val updates: MutableMap<String, Any> = mutableMapOf()
        if (videoUpdates.description.isNotEmpty()) {
            updates["description"] = videoUpdates.description
        }
        if (videoUpdates.title.isNotEmpty() && videoUpdates.searchIndex.isNotEmpty()) {
            updates["title"] = videoUpdates.title
            updates["searchIndex"] = videoUpdates.searchIndex
        }

        CompletableFuture.supplyAsync {
            ref.update(updates)
        }

        return getVideo(videoId)
    }

    override fun deleteVideo(videoId: String) {
        val ref: DocumentReference = collectionReference.document(videoId)
        getVideo(videoId)

        ref.delete()
    }
}