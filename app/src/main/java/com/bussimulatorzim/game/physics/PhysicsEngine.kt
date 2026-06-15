package com.bussimulatorzim.game.physics

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector3(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f
) {
    fun length(): Float = sqrt(x * x + y * y + z * z)
    fun normalized(): Vector3 {
        val len = length()
        return if (len > 0) Vector3(x / len, y / len, z / len) else this
    }
    fun dot(other: Vector3): Float = x * other.x + y * other.y + z * other.z
}

data class Quaternion(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
    var w: Float = 1f
)

class PhysicsEngine {
    private val gravity = Vector3(0f, -9.81f, 0f)
    private val vehicles = mutableListOf<VehiclePhysics>()
    
    fun update(deltaTime: Float) {
        for (vehicle in vehicles) {
            updateVehicle(vehicle, deltaTime)
        }
    }
    
    private fun updateVehicle(vehicle: VehiclePhysics, deltaTime: Float) {
        // Apply forces
        vehicle.velocity.x += vehicle.acceleration.x * deltaTime
        vehicle.velocity.y += vehicle.acceleration.y * deltaTime
        vehicle.velocity.z += vehicle.acceleration.z * deltaTime
        
        // Apply gravity
        vehicle.velocity.y += gravity.y * deltaTime
        
        // Update position
        vehicle.position.x += vehicle.velocity.x * deltaTime
        vehicle.position.y += vehicle.velocity.y * deltaTime
        vehicle.position.z += vehicle.velocity.z * deltaTime
        
        // Apply friction
        vehicle.velocity.x *= 0.99f
        vehicle.velocity.z *= 0.99f
        
        // Ground collision
        if (vehicle.position.y < 0) {
            vehicle.position.y = 0f
            vehicle.velocity.y = 0f
        }
    }
    
    fun addVehicle(vehicle: VehiclePhysics) {
        vehicles.add(vehicle)
    }
    
    fun removeVehicle(vehicle: VehiclePhysics) {
        vehicles.remove(vehicle)
    }
}

class VehiclePhysics {
    var position = Vector3()
    var rotation = Quaternion()
    var velocity = Vector3()
    var acceleration = Vector3()
    var mass = 15000f // kg (bus)
    var engineForce = 0f
    var brakeTorque = 0f
    var steeringAngle = 0f
    var speed = 0f
    var rpm = 0f
    var gear = 0
    var wheelRotation = 0f
    
    // Damage system
    var engineHealth = 100f
    var bodyHealth = 100f
    var windshieldHealth = 100f
    var tireCondition = floatArrayOf(100f, 100f, 100f, 100f) // 4 tires
    var fuelLevel = 100f
    var tirePressure = floatArrayOf(100f, 100f, 100f, 100f)
    
    fun calculateSpeed() {
        speed = sqrt(velocity.x * velocity.x + velocity.z * velocity.z)
    }
    
    fun takeDamage(damageAmount: Float, damageType: String) {
        when (damageType) {
            "collision" -> {
                bodyHealth -= damageAmount
                windshieldHealth -= damageAmount * 0.5f
            }
            "tire_puncture" -> {
                tireCondition[0] -= damageAmount
            }
            "engine_overheat" -> {
                engineHealth -= damageAmount
            }
            "wear" -> {
                engineHealth -= damageAmount * 0.1f
                tireCondition.indices.forEach { i ->
                    tireCondition[i] -= damageAmount * 0.05f
                }
            }
        }
    }
}
