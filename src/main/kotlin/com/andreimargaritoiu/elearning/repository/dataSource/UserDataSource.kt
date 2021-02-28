package com.andreimargaritoiu.elearning.repository.dataSource

import com.andreimargaritoiu.elearning.model.User
import com.andreimargaritoiu.elearning.repository.generic.UserRepository
import com.andreimargaritoiu.elearning.service.FirebaseInitialize
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class UserDataSource(firebaseInitialize: FirebaseInitialize): UserRepository {

    val collectionName = "Users"
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
        val documentReference: DocumentReference = collectionReference.document(userId)
        val documentSnapshot: ApiFuture<DocumentSnapshot> = documentReference.get()

        return documentSnapshot.get().toObject(User::class.java)
                ?: throw NoSuchElementException("Could not find user with id = $userId")
    }

    override fun addUser(user: User): User {
        val users = mutableListOf<User>()
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        querySnapshot.get().documents.forEach {
            users.add(it.toObject(User::class.java))
        }
        users.forEach {
            if (it.email == user.email) throw IllegalArgumentException("User with email = ${user.email} already exists")
        }

        collectionReference.document().set(user)
        return user
    }

    override fun updateUser(userId: String, user: User): User {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == userId } == null)
            throw NoSuchElementException("Could not find user with id = $userId")

        collectionReference.document(userId).set(user)
        return user
    }

    override fun deleteUser(userId: String) {
        val querySnapshot: ApiFuture<QuerySnapshot> = collectionReference.get()
        if (querySnapshot.get().documents.find { it.id == userId } == null)
            throw NoSuchElementException("Could not find user with id = $userId")

        collectionReference.document(userId).delete()
    }
}