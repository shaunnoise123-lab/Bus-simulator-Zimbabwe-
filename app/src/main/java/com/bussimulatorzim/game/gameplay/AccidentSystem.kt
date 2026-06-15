package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3

data class Accident(
    val id: Int,
    val position: Vector3,
    val severity: Int, // 1-5
    val time: Long = System.currentTimeMillis(),
    var policeArrived: Boolean = false,
    var ambulanceArrived: Boolean = false,
    var injuredCount: Int = 0,
    var compensationAmount: Float = 0f
)

class AccidentSystem {
    private val accidents = mutableListOf<Accident>()
    private var accidentIdCounter = 0
    private val accidentChance = 0.0001f // Very low chance per frame
    private val weatherAccidentMultiplier = 2f // 2x more accidents in bad weather
    private val speedAccidentMultiplier = 3f // 3x more accidents if speeding
    
    fun update(deltaTime: Float, playerSpeed: Float, weatherDanger: Float) {
        // Check for new accidents
        var accidentChanceModified = accidentChance
        
        if (playerSpeed > 120f) {
            accidentChanceModified *= speedAccidentMultiplier
        }
        
        if (weatherDanger > 0.5f) {
            accidentChanceModified *= weatherAccidentMultiplier
        }
        
        if (Math.random() < accidentChanceModified) {
            createAccident()
        }
        
        // Update existing accidents
        accidents.forEach { accident ->
            if (!accident.policeArrived && System.currentTimeMillis() - accident.time > 10000) {
                accident.policeArrived = true
            }
            if (!accident.ambulanceArrived && System.currentTimeMillis() - accident.time > 5000) {
                accident.ambulanceArrived = true
            }
        }
        
        // Remove old accidents (after 5 minutes)
        accidents.removeAll { System.currentTimeMillis() - it.time > 300000 }
    }
    
    private fun createAccident() {
        val severity = (Math.random() * 5 + 1).toInt()
        val injuredCount = severity * 2
        val compensation = injuredCount * 100f + severity * 50f
        
        val accident = Accident(
            id = accidentIdCounter++,
            position = Vector3(
                (Math.random() * 400 - 200).toFloat(),
                0f,
                (Math.random() * 400 - 200).toFloat()
            ),
            severity = severity,
            injuredCount = injuredCount,
            compensationAmount = compensation
        )
        
        accidents.add(accident)
    }
    
    fun reportAccident(position: Vector3): Accident? {
        return accidents.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            kotlin.math.sqrt(dx * dx + dz * dz) < 100f
        }
    }
    
    fun getAccidents() = accidents
}
