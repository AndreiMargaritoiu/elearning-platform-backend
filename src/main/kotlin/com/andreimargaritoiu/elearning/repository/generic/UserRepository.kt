package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.User

interface UserRepository {

    fun getUsers(): Collection<User>
    fun getUser(userId: String): User
    fun addUser(User: User): User
    fun updateUser(userId: String, User: User): User
    fun deleteUser(userId: String)

}