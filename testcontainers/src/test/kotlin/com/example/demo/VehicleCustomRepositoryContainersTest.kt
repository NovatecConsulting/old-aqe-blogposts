package com.example.demo

import com.example.demo.persistence.VehicleCustomRepository
import com.example.demo.persistence.VehicleEntity
import com.example.demo.persistence.VehicleNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import util.IntegrationTest
import java.util.*

@SpringBootTest
@Testcontainers
@IntegrationTest
/**
 * This test class demonstrates Integration test with usage of test containers
 */
class VehicleCustomRepositoryContainersTest {

    @Autowired
    private lateinit var vehicleCustomRepository: VehicleCustomRepository

    @Container
    private val redisContainer = KFixedHostPortGenericContainer("redis:latest")
        .withFixedExposedPort(6379, 6379)


    @Test
    fun `saving vehicle without an error`() {
        //prepare data to be saved in Redis
        val vehicleId = UUID.randomUUID().toString()
        val vehicleEntity = VehicleEntity(
            id = vehicleId,
            model = "BMW",
            colour = "black"
        )

        vehicleCustomRepository.save(vehicleEntity)

        //Verify that vehicle is being saved
        val result = vehicleCustomRepository.findById(vehicleId)
        assertThat(result).isEqualTo(vehicleEntity)
    }

    @Test
    fun `looking for non existing vehicle`() {
        val nonExistingId = UUID.randomUUID().toString()

        //assert that VehicleNotFoundException will be thrown when no vehicle exists for the given id
        assertThrows<VehicleNotFoundException> {
            vehicleCustomRepository.findById(nonExistingId)
        }
    }
}