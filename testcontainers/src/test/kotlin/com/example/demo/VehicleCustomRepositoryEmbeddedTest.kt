package com.example.demo

import com.example.demo.persistence.VehicleCustomRepository
import com.example.demo.persistence.VehicleEntity
import com.example.demo.persistence.VehicleNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import redis.embedded.RedisServer
import util.IntegrationTest
import java.util.*

@SpringBootTest
@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class) // extension needed for Junit 5
@IntegrationTest
/**
 * This test class demonstrates Integration test with usage of embedded Redis
 */
class VehicleCustomRepositoryEmbeddedTest {

    @Autowired
    private lateinit var vehicleCustomRepository: VehicleCustomRepository

    companion object {
        private val redisServer = RedisServer(6379)

        @BeforeAll
        @JvmStatic
        fun setUp() {
            redisServer.start()
        }

        @AfterAll
        @JvmStatic
        fun reset() {
            redisServer.stop()
        }
    }

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
        Assertions.assertThat(result).isEqualTo(vehicleEntity)
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