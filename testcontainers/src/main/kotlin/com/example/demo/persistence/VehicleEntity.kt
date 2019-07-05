package com.example.demo.persistence

import org.springframework.data.annotation.Id
import java.io.Serializable

data class VehicleEntity(
    @Id
    val id: String = "",
    val model: String = "",
    val colour: String = ""
) : Serializable