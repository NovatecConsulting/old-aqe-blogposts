package com.example.demo

import com.example.demo.persistence.VehicleCustomRepository
import com.example.demo.persistence.VehicleEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/vehicle")
class VehicleController(
    private val vehicleCustomRepository: VehicleCustomRepository
) {
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getVehicle(@PathVariable("id") id: String): VehiclePayload {
        val vehicleEntity = vehicleCustomRepository.findById(id)
        return VehiclePayload(
            model = vehicleEntity.model,
            colour = vehicleEntity.colour
        )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVehicle(@RequestBody vehiclePayload: VehiclePayload) {
        val vehicleEntity = VehicleEntity(
            id = UUID.randomUUID().toString(),
            model = vehiclePayload.model,
            colour = vehiclePayload.colour
        )
        vehicleCustomRepository.save(vehicleEntity)
    }
}