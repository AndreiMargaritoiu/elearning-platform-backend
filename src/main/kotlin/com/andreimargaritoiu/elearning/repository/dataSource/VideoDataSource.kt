package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.model.Video
import com.andreimargaritoiu.elearning.repository.generic.VideoRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.NoSuchElementException

@Repository
class VideoDataSource(firebaseInitialize: FirebaseInitialize) : VideoRepository {

    private final val collectionName = "videos"
    private final val playlistCollectionName = "playlists"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)
    val playlistsCollectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(playlistCollectionName)

    override fun getVideos(uid: Optional<String>, playlistId: Optional<String>): Collection<Video> {
        val videos = mutableListOf<Video>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            videos.add(it.toObject(Video::class.java))
        }

        if (!uid.isEmpty) {
            return videos.filter { it.uid == uid.get() }
        }

        if (!playlistId.isEmpty) {
            val playlistQuerySnapshot: ApiFuture<QuerySnapshot> = playlistsCollectionReference.get()
            playlistQuerySnapshot.get().documents.forEach {
                val currentPlaylist: Playlist = it.toObject(Playlist::class.java);
                if (currentPlaylist.id == playlistId.get()) {
                    return videos.filter { elem -> currentPlaylist.videoRefs.contains(elem.id)  }
                }
            }
            throw NoSuchElementException("Could not find playlist with id = ${playlistId.get()}")
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
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == video.id } == null)
            throw NoSuchElementException("Could not find video with id = $video.id")

        collectionReference.document(video.id).set(video)
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