package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3
import kotlin.math.sqrt

data class SpeedCamera(
    val id: Int,
    val position: Vector3,
    val speedLimit: Int,
    val finePerKmh: Float = 10f
) {
    var lastPlayerCapture: Long = 0L
    var photosCapture = 0
    
    fun checkSpeed(speed: Float): Float {
        return if (speed > speedLimit) {
            (speed - speedLimit) * finePerKmh
        } else 0f
    }
}

class SpeedCameraNetwork {
    private val cameras = mutableListOf<SpeedCamera>()
    private var cameraIdCounter = 0
    
    fun initialize() {
        cameras.addAll(listOf(
            SpeedCamera(cameraIdCounter++, Vector3(-100f, 0f, 0f), 80),
            SpeedCamera(cameraIdCounter++, Vector3(-200f, 0f, 0f), 100),
            SpeedCamera(cameraIdCounter++, Vector3(50f, 100f, 0f), 80),
            SpeedCamera(cameraIdCounter++, Vector3(-50f, -150f, 0f), 60),
            SpeedCamera(cameraIdCounter++, Vector3(0f, 50f, 100f), 90)
        ))
    }
    
    fun getNearestCamera(position: Vector3, range: Float = 150f): SpeedCamera? {
        return cameras.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            sqrt(dx * dx + dz * dz) <= range
        }
    }
    
    fun getCameras() = cameras
}
