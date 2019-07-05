package com.example.demo.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class VehicleCustomRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) : VehicleCustomRepository {

    override fun save(vehicleEntity: VehicleEntity) {
        redisTemplate.opsForValue().set(vehicleEntity.id, vehicleEntity);
    }

    override fun findById(id: String): VehicleEntity {
        return redisTemplate.opsForValue().get(id)?.let {
            objectMapper.convertValue(it, VehicleEntity::class.java)
        } ?: throw VehicleNotFoundException("No vehicle for id = '$id' has been found.")
    }
}