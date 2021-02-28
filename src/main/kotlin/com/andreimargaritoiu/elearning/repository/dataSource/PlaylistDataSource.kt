package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.Playlist
import com.andreimargaritoiu.elearning.repository.generic.PlaylistRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class PlaylistDataSource(firebaseInitialize: FirebaseInitialize): PlaylistRepository {

    val collectionName = "Playlists"
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
        val documentReference: DocumentReference = collectionReference.document(playlistId)
        val documentSnapshot: ApiFuture<DocumentSnapshot> = documentReference.get()

        return documentSnapshot.get().toObject(Playlist::class.java)
                ?: throw NoSuchElementException("Could not find playlist with id = $playlistId")
    }

    override fun addPlaylist(playlist: Playlist): Playlist {
        val playlists = mutableListOf<Playlist>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            playlists.add(it.toObject(Playlist::class.java))
        }
        playlists.forEach {
            if (it.name == playlist.name)
                throw IllegalArgumentException("Playlist with name = ${playlist.name} already exists")
        }

        collectionReference.document().set(playlist)
        return playlist
    }

    override fun updatePlaylist(playlistId: String, playlist: Playlist): Playlist {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == playlistId } == null)
            throw NoSuchElementException("Could not find playlist with id = $playlistId")

        collectionReference.document(playlistId).set(playlist)
        return playlist
    }

    override fun deletePlaylist(playlistId: String) {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == playlistId } == null)
            throw NoSuchElementException("Could not find playlist with id = $playlistId")

        collectionReference.document(playlistId).delete()
    }
}