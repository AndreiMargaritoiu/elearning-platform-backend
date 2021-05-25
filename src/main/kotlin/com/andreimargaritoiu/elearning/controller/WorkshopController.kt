package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Workshop
import com.andreimargaritoiu.elearning.model.builders.WorkshopBuilder
import com.andreimargaritoiu.elearning.service.WorkshopService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("api/workshops")
class WorkshopController(private val workshopService: WorkshopService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getWorkshops(): Collection<Workshop> = workshopService.getWorkshops()

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addWorkshop(@RequestBody workshopBuilder: WorkshopBuilder): Workshop =
        workshopService.addWorkshop(workshopBuilder)


    @DeleteMapping("/{workshopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteWorkshop(@PathVariable workshopId: String) = workshopService.deleteWorkshop(workshopId)

}