package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.User
import com.andreimargaritoiu.elearning.model.updates.UserUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.UserDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userDataSource: UserDataSource) {

    fun getUsers(followedBy: Optional<String>): Collection<User> {
        val users: Collection<User> = userDataSource.getUsers()

        if (!followedBy.isEmpty) {
            val currentUser: User = userDataSource.getUser(followedBy.get())
            return users.filter { currentUser.following.contains(it.uid) }
        }

        return users
    }
    fun getUser(userId: String): User = userDataSource.getUser(userId)
    fun addUser(user: User): User = userDataSource.addUser(user)
    fun updateUser(userId: String, userUpdates: UserUpdates): User = userDataSource.updateUser(userId, userUpdates) 
    fun deleteUser(userId: String) = userDataSource.deleteUser(userId)

}