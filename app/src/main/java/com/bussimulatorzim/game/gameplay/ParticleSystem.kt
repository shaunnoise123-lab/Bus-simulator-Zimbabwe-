package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3
import kotlin.math.cos
import kotlin.math.sin

enum class ParticleType {
    DUST,
    SMOKE,
    FIRE,
    RAIN,
    SNOW,
    SPARKS,
    TIRE_MARKS
}

data class Particle(
    val position: Vector3,
    val velocity: Vector3,
    val type: ParticleType,
    val lifetime: Float,
    var age: Float = 0f,
    val size: Float = 0.5f,
    val opacity: Float = 1f
) {
    val isAlive: Boolean get() = age < lifetime
    fun getOpacity(): Float = opacity * (1f - age / lifetime)
}

class ParticleSystem {
    private val particles = mutableListOf<Particle>()
    private val maxParticles = 10000
    
    fun update(deltaTime: Float) {
        particles.forEach { it.age += deltaTime }
        particles.removeAll { !it.isAlive }
    }
    
    fun emitDustCloud(position: Vector3, intensity: Float) {
        val particleCount = (intensity * 50).toInt()
        repeat(particleCount) {
            val angle = Math.random() * 2 * Math.PI
            val velocity = Vector3(
                (cos(angle) * intensity).toFloat(),
                (Math.random() * intensity).toFloat(),
                (sin(angle) * intensity).toFloat()
            )
            addParticle(
                Particle(
                    position = position.copy(),
                    velocity = velocity,
                    type = ParticleType.DUST,
                    lifetime = 5f,
                    size = (0.1f + Math.random() * 0.4f).toFloat()
                )
            )
        }
    }
    
    fun emitSmokeCloud(position: Vector3, intensity: Float) {
        val particleCount = (intensity * 30).toInt()
        repeat(particleCount) {
            val velocity = Vector3(
                (Math.random() * intensity - intensity / 2).toFloat(),
                (Math.random() * intensity * 2).toFloat(),
                (Math.random() * intensity - intensity / 2).toFloat()
            )
            addParticle(
                Particle(
                    position = position.copy(),
                    velocity = velocity,
                    type = ParticleType.SMOKE,
                    lifetime = 3f,
                    opacity = 0.7f
                )
            )
        }
    }
    
    fun emitFireParticles(position: Vector3, intensity: Float) {
        val particleCount = (intensity * 20).toInt()
        repeat(particleCount) {
            val velocity = Vector3(
                (Math.random() * intensity - intensity / 2).toFloat(),
                (Math.random() * intensity * 1.5).toFloat(),
                (Math.random() * intensity - intensity / 2).toFloat()
            )
            addParticle(
                Particle(
                    position = position.copy(),
                    velocity = velocity,
                    type = ParticleType.FIRE,
                    lifetime = 2f
                )
            )
        }
    }
    
    fun emitRain(position: Vector3, intensity: Float) {
        val particleCount = (intensity * 100).toInt()
        repeat(particleCount) {
            val velocity = Vector3(
                (Math.random() * 2 - 1).toFloat(),
                -(Math.random() * intensity * 5).toFloat(),
                (Math.random() * 2 - 1).toFloat()
            )
            addParticle(
                Particle(
                    position = position.copy(),
                    velocity = velocity,
                    type = ParticleType.RAIN,
                    lifetime = 5f,
                    size = 0.2f,
                    opacity = 0.6f
                )
            )
        }
    }
    
    fun emitTireMarks(position: Vector3, direction: Vector3) {
        addParticle(
            Particle(
                position = position.copy(),
                velocity = direction,
                type = ParticleType.TIRE_MARKS,
                lifetime = 10f,
                size = 1f,
                opacity = 0.5f
            )
        )
    }
    
    private fun addParticle(particle: Particle) {
        if (particles.size < maxParticles) {
            particles.add(particle)
        }
    }
    
    fun getParticles() = particles
    fun getParticleCount() = particles.size
}
