package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3
import com.bussimulatorzim.game.physics.VehiclePhysics

enum class PoliceAction {
    IDLE,
    PATROL,
    INVESTIGATING,
    PURSUING,
    BLOCKING
}

data class PoliceVehicle(
    val vehicle: VehiclePhysics,
    var action: PoliceAction = PoliceAction.PATROL,
    var targetPlayerDistance: Float = Float.MAX_VALUE,
    var pursuitActive: Boolean = false,
    var pursuitTime: Float = 0f
)

class PoliceSystem {
    private val policeVehicles = mutableListOf<PoliceVehicle>()
    private var playerWantedLevel = 0
    private var pursuitActive = false
    private var pursuitTimer = 0f
    
    fun addPoliceVehicle(vehicle: VehiclePhysics) {
        policeVehicles.add(PoliceVehicle(vehicle))
    }
    
    fun update(deltaTime: Float, playerPosition: Vector3, playerSpeed: Float) {
        for (police in policeVehicles) {
            updatePoliceVehicle(police, deltaTime, playerPosition, playerSpeed)
        }
        
        if (pursuitActive) {
            pursuitTimer += deltaTime
            if (pursuitTimer > 300f) {
                endPursuit()
            }
        }
    }
    
    private fun updatePoliceVehicle(
        police: PoliceVehicle,
        deltaTime: Float,
        playerPosition: Vector3,
        playerSpeed: Float
    ) {
        val dx = playerPosition.x - police.vehicle.position.x
        val dz = playerPosition.z - police.vehicle.position.z
        val distance = kotlin.math.sqrt(dx * dx + dz * dz)
        
        police.targetPlayerDistance = distance
        
        when {
            distance < 50f && playerWantedLevel > 0 -> {
                police.action = PoliceAction.PURSUING
                startPursuit()
            }
            distance < 200f && playerWantedLevel > 0 -> {
                police.action = PoliceAction.INVESTIGATING
            }
            else -> {
                police.action = PoliceAction.PATROL
            }
        }
    }
    
    fun startPursuit() {
        pursuitActive = true
        pursuitTimer = 0f
    }
    
    fun endPursuit() {
        pursuitActive = false
        playerWantedLevel = 0
        policeVehicles.forEach { it.pursuitActive = false }
    }
    
    fun increaseWantedLevel() {
        playerWantedLevel = (playerWantedLevel + 1).coerceAtMost(5)
        if (playerWantedLevel > 0) {
            startPursuit()
        }
    }
    
    fun decreaseWantedLevel() {
        playerWantedLevel = (playerWantedLevel - 1).coerceAtLeast(0)
    }
    
    fun getWantedLevel() = playerWantedLevel
    fun isPursuitActive() = pursuitActive
    fun getPoliceVehicles() = policeVehicles
}
