package com.example.demo.persistence

interface VehicleCustomRepository {
    fun save(vehicleEntity: VehicleEntity)

    fun findById(id: String): VehicleEntity
}