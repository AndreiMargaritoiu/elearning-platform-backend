package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.models.User
import com.andreimargaritoiu.elearning.model.updates.UserUpdates
import com.andreimargaritoiu.elearning.repository.generic.UserRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import java.util.*

@Repository
class UserDataSource(firebaseInitialize: FirebaseInitialize): UserRepository {

    private final val collectionName = "users"
    val collectionReference: CollectionReference = firebaseInitialize.getFirebase().collection(collectionName)

    override fun getUsers(): Collection<User> {
        val users = mutableListOf<User>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            users.add(it.toObject(User::class.java))
        }

        return users;
    }

    override fun getUser(userId: String): User {
        val document: ApiFuture<DocumentSnapshot> = collectionReference.document(userId).get()

        return document.get().toObject(User::class.java)
                ?: throw NoSuchElementException("Could not find user with id = $userId")
    }

    override fun addUser(user: User): User {
        val users = getUsers()
        users.forEach {
            if (it.email == user.email) throw IllegalArgumentException("User with email = ${user.email} already exists")
        }

        collectionReference.document().set(user)
        return user
    }

    override fun updateUser(userId: String, userUpdates: UserUpdates): User {
        val ref: DocumentReference = collectionReference.document(userId)
        val updates: MutableMap<String, Any> = mutableMapOf()
        if (userUpdates.photoUrl.isNotEmpty()) {
            updates["photoUrl"] = userUpdates.photoUrl
        }
        if (userUpdates.following.isNotEmpty()) {
            updates["following"] = userUpdates.following
        }

//        lateinit var user: User;
//
//        CompletableFuture.runAsync {
//            ref.update(updates)
//        }.thenRun {
//            user = getUser(userId)
//        }
//
//        return user;
//        updateFbUser(ref, updates)

        ref.update(updates)
        return getUser(userId)
    }

//    suspend fun updateFbUser(ref: DocumentReference, updates: MutableMap<String, Any>) {
//        ref.update(updates)
//    }

    override fun deleteUser(userId: String) {
        val ref: DocumentReference = collectionReference.document(userId)
        getUser(userId)

        ref.delete()
    }
}