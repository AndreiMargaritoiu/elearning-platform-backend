package com.andreimargaritoiu.elearning.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.storage.Bucket
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.cloud.StorageClient
import org.springframework.stereotype.Service
import java.io.FileInputStream
import javax.annotation.PostConstruct

@Service
class FirebaseInitialize {

    @PostConstruct
     fun initialize() {
        try {
            val serviceAccount = FileInputStream("./elearning-platform-e75ed-firebase-adminsdk-63ocv-fa29199ad2.json")
            val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://elearning-platform-e75ed.firebaseio.com")
                    .setStorageBucket("elearning-platform-e75ed.appspot.com")
                    .build()
            FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFirebase(): Firestore = FirestoreClient.getFirestore()
    fun getStorage(): Bucket = StorageClient.getInstance().bucket()
}