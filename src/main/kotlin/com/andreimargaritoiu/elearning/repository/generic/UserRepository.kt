package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.models.User
import com.andreimargaritoiu.elearning.model.updates.UserUpdates

interface UserRepository {

    fun getUsers(): Collection<User>
    fun getUser(userId: String): User
    fun addUser(user: User): User
    fun updateUser(userId: String, userUpdates: UserUpdates): User
    fun deleteUser(userId: String)

}