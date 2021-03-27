package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.models.Playlist
import com.andreimargaritoiu.elearning.model.updates.PlaylistUpdates
import com.andreimargaritoiu.elearning.repository.generic.PlaylistRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import kotlin.NoSuchElementException

@Repository
class PlaylistDataSource(firebaseInitialize: FirebaseInitialize): PlaylistRepository {

    private final val collectionName = "playlists"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getPlaylists(): Collection<Playlist> {
        val playlists = mutableListOf<Playlist>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            playlists.add(it.toObject(Playlist::class.java))
        }

        return playlists;
    }

    override fun getPlaylist(playlistId: String): Playlist {
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(playlistId).get()

        return document.get().toObject(Playlist::class.java)
                ?: throw NoSuchElementException("Could not find playlist with id = $playlistId")
    }

    override fun addPlaylist(playlist: Playlist): Playlist {
        val playlists = getPlaylists()
        playlists.forEach {
            if (it.title == playlist.title)
                throw IllegalArgumentException("Playlist with title = ${playlist.title} already exists")
        }

        val ref: DocumentReference = collectionReference.document();
        ref.set(playlist)

        return playlist
    }

    override fun updatePlaylist(playlistId: String, playlistUpdates: PlaylistUpdates): Playlist {
        val ref: DocumentReference = collectionReference.document(playlistId)
        val updates: MutableMap<String, Any> = mutableMapOf()
        if (playlistUpdates.description.isNotEmpty()) {
            updates["description"] = playlistUpdates.description
        }
        if (playlistUpdates.title.isNotEmpty() && playlistUpdates.searchIndex.isNotEmpty()) {
            updates["title"] = playlistUpdates.title
            updates["searchIndex"] = playlistUpdates.searchIndex
        }
        if (playlistUpdates.videoRefs.isNotEmpty()) {
            updates["videoRefs"] = playlistUpdates.videoRefs
        }

        ref.update(updates)

        return getPlaylist(playlistId)
    }

    override fun deletePlaylist(playlistId: String) {
        val ref: DocumentReference = collectionReference.document(playlistId)
        getPlaylist(playlistId)

        ref.delete()
    }
}