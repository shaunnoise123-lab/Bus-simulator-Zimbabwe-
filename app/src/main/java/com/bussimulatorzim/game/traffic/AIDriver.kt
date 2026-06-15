package com.bussimulatorzim.game.traffic

import com.bussimulatorzim.game.physics.VehiclePhysics
import com.bussimulatorzim.game.physics.Vector3
import kotlin.math.sqrt

enum class DriverBehavior {
    AGGRESSIVE,      // Dangerous, fast, no rules
    NORMAL,          // Follows rules mostly
    CAUTIOUS,        // Slow, careful, follows all rules
    POLICE,          // Enforcement focused
    EMERGENCY        // Ambulance/Fire truck behavior
}

class AIDriver(val vehicle: VehiclePhysics, val behavior: DriverBehavior) {
    var targetSpeed = 80f
    var currentRoute = mutableListOf<Vector3>()
    var routeIndex = 0
    var isChasing = false
    var chaseTarget: VehiclePhysics? = null
    var stoppingDistance = 20f
    var reactionTime = 0.5f
    var reactionTimer = 0f
    
    fun update(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        reactionTimer += deltaTime
        
        when (behavior) {
            DriverBehavior.AGGRESSIVE -> updateAggressive(deltaTime, otherVehicles)
            DriverBehavior.NORMAL -> updateNormal(deltaTime, otherVehicles)
            DriverBehavior.CAUTIOUS -> updateCautious(deltaTime, otherVehicles)
            DriverBehavior.POLICE -> updatePolice(deltaTime, otherVehicles)
            DriverBehavior.EMERGENCY -> updateEmergency(deltaTime, otherVehicles)
        }
    }
    
    private fun updateAggressive(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        targetSpeed = 140f
        vehicle.engineForce = 8000f
        
        for (other in otherVehicles) {
            val distance = distanceTo(other)
            if (distance < 50f && distance > 0f) {
                vehicle.steeringAngle = 0.3f
            }
        }
    }
    
    private fun updateNormal(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        targetSpeed = 100f
        vehicle.engineForce = 5000f
        
        for (other in otherVehicles) {
            val distance = distanceTo(other)
            if (distance < stoppingDistance) {
                vehicle.brakeTorque = 3000f
                vehicle.engineForce = 0f
            }
        }
    }
    
    private fun updateCautious(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        targetSpeed = 60f
        vehicle.engineForce = 3000f
        vehicle.brakeTorque = 2000f
    }
    
    private fun updatePolice(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        if (isChasing && chaseTarget != null) {
            targetSpeed = 140f
            vehicle.engineForce = 9000f
        } else {
            updateNormal(deltaTime, otherVehicles)
        }
    }
    
    private fun updateEmergency(deltaTime: Float, otherVehicles: List<VehiclePhysics>) {
        targetSpeed = 150f
        vehicle.engineForce = 10000f
    }
    
    private fun distanceTo(other: VehiclePhysics): Float {
        val dx = vehicle.position.x - other.position.x
        val dz = vehicle.position.z - other.position.z
        return sqrt(dx * dx + dz * dz)
    }
    
    fun startChase(target: VehiclePhysics) {
        isChasing = true
        chaseTarget = target
    }
    
    fun stopChase() {
        isChasing = false
        chaseTarget = null
    }
}
