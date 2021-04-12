package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.User
import com.andreimargaritoiu.elearning.model.updates.UserUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.UserDataSource
import org.springframework.stereotype.Service

@Service
class UserService(private val userDataSource: UserDataSource) {

    fun getUsers(): Collection<User> = userDataSource.getUsers()
    fun getUser(userId: String): User = userDataSource.getUser(userId)
    fun addUser(user: User): User = userDataSource.addUser(user)
    fun updateUser(userId: String, userUpdates: UserUpdates): User = userDataSource.updateUser(userId, userUpdates)
    fun deleteUser(userId: String) = userDataSource.deleteUser(userId)

}