package com.bussimulatorzim.game.physics

import kotlin.math.sqrt

data class CollisionInfo(
    val vehicle1: VehiclePhysics,
    val vehicle2: VehiclePhysics,
    val collisionForce: Float,
    val contactPoint: Vector3
)

class CollisionDetector {
    private val collisions = mutableListOf<CollisionInfo>()
    
    fun detectCollisions(vehicles: List<VehiclePhysics>): List<CollisionInfo> {
        collisions.clear()
        
        for (i in vehicles.indices) {
            for (j in i + 1 until vehicles.size) {
                val collision = checkCollision(vehicles[i], vehicles[j])
                if (collision != null) {
                    collisions.add(collision)
                }
            }
        }
        
        return collisions
    }
    
    private fun checkCollision(v1: VehiclePhysics, v2: VehiclePhysics): CollisionInfo? {
        val dx = v2.position.x - v1.position.x
        val dy = v2.position.y - v1.position.y
        val dz = v2.position.z - v1.position.z
        
        val distance = sqrt(dx * dx + dy * dy + dz * dz)
        val minDistance = 5f
        
        if (distance < minDistance) {
            val relativeVelocity = Vector3(
                v2.velocity.x - v1.velocity.x,
                v2.velocity.y - v1.velocity.y,
                v2.velocity.z - v1.velocity.z
            )
            val collisionForce = relativeVelocity.length() * (v1.mass + v2.mass) / 2
            
            val contactPoint = Vector3(
                (v1.position.x + v2.position.x) / 2,
                (v1.position.y + v2.position.y) / 2,
                (v1.position.z + v2.position.z) / 2
            )
            
            return CollisionInfo(
                vehicle1 = v1,
                vehicle2 = v2,
                collisionForce = collisionForce,
                contactPoint = contactPoint
            )
        }
        
        return null
    }
    
    fun checkTerrainCollision(vehicle: VehiclePhysics, terrain: TerrainData): Boolean {
        return terrain.isOnRoad(vehicle.position)
    }
    
    fun checkBuildingCollision(vehicle: VehiclePhysics, buildings: List<Building>): Building? {
        for (building in buildings) {
            if (distance(vehicle.position, building.position) < building.collisionRadius + 3f) {
                return building
            }
        }
        return null
    }
    
    private fun distance(p1: Vector3, p2: Vector3): Float {
        val dx = p2.x - p1.x
        val dz = p2.z - p1.z
        return sqrt(dx * dx + dz * dz)
    }
}

data class Building(
    val name: String,
    val position: Vector3,
    val collisionRadius: Float = 10f
)

data class TerrainData(
    val roadNetwork: List<Road>
) {
    fun isOnRoad(position: Vector3): Boolean {
        for (road in roadNetwork) {
            if (distance(position, road.startPos) < 50f || distance(position, road.endPos) < 50f) {
                return true
            }
        }
        return false
    }
    
    private fun distance(p1: Vector3, p2: Vector3): Float {
        val dx = p2.x - p1.x
        val dz = p2.z - p1.z
        return sqrt(dx * dx + dz * dz)
    }
}

data class Road(
    val name: String,
    val startPos: Vector3,
    val endPos: Vector3,
    val type: String
)
