package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.User
import com.andreimargaritoiu.elearning.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getUsers(): Collection<User> = userService.getUsers()

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): User = userService.getUser(userId)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody User: User): User = userService.addUser(User)

    @PatchMapping("/{userId}")
    fun getUser(@PathVariable userId: String, @RequestBody User: User): User =
            userService.updateUser(userId, User)

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: String): Unit = userService.deleteUser(userId) // Unit = void

}