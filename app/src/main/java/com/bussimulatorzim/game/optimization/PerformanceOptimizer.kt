package com.bussimulatorzim.game.optimization

import kotlin.math.sqrt

class PerformanceMonitor {
    private var frameCount = 0
    private var totalFrameTime = 0f
    private var lastFrameTime = System.nanoTime()
    private var fps = 60f
    private var averageFrameTime = 16.67f
    private val maxFrameTimes = FloatArray(60)
    private var frameIndex = 0
    
    fun update() {
        val currentTime = System.nanoTime()
        val deltaTime = (currentTime - lastFrameTime) / 1_000_000f
        lastFrameTime = currentTime
        
        frameCount++
        totalFrameTime += deltaTime
        maxFrameTimes[frameIndex++ % 60] = deltaTime
        
        if (frameCount >= 60) {
            averageFrameTime = totalFrameTime / 60f
            fps = 1000f / averageFrameTime
            frameCount = 0
            totalFrameTime = 0f
        }
    }
    
    fun getFPS() = fps
    fun getAverageFrameTime() = averageFrameTime
    fun getMaxFrameTime() = maxFrameTimes.maxOrNull() ?: 0f
    fun getMinFrameTime() = maxFrameTimes.minOrNull() ?: 0f
}

class MemoryOptimizer {
    private val runtime = Runtime.getRuntime()
    
    fun getMemoryUsage(): Float {
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val usedMemory = totalMemory - freeMemory
        return (usedMemory.toFloat() / totalMemory.toFloat()) * 100
    }
    
    fun getMemoryUsageMB(): Float {
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val usedMemory = totalMemory - freeMemory
        return usedMemory / (1024 * 1024).toFloat()
    }
    
    fun getMaxMemoryMB(): Float {
        return runtime.maxMemory() / (1024 * 1024).toFloat()
    }
    
    fun shouldGarbageCollect(): Boolean {
        return getMemoryUsage() > 85f
    }
    
    fun forceGarbageCollection() {
        System.gc()
    }
}

class ObjectPool<T>(private val factory: () -> T) {
    private val availableObjects = mutableListOf<T>()
    private val activeObjects = mutableListOf<T>()
    private val initialPoolSize = 100
    
    init {
        repeat(initialPoolSize) {
            availableObjects.add(factory())
        }
    }
    
    fun acquire(): T {
        return if (availableObjects.isNotEmpty()) {
            availableObjects.removeAt(availableObjects.size - 1).also { activeObjects.add(it) }
        } else {
            factory().also { activeObjects.add(it) }
        }
    }
    
    fun release(obj: T) {
        activeObjects.remove(obj)
        if (availableObjects.size < initialPoolSize * 2) {
            availableObjects.add(obj)
        }
    }
    
    fun clear() {
        availableObjects.clear()
        activeObjects.clear()
    }
    
    fun getStats(): Pair<Int, Int> = Pair(availableObjects.size, activeObjects.size)
}

class LevelOfDetailSystem {
    fun calculateLOD(distance: Float): Int {
        return when {
            distance < 50f -> 0   // Highest detail
            distance < 150f -> 1  // Medium detail
            distance < 300f -> 2  // Low detail
            else -> 3              // Minimal detail
        }
    }
    
    fun shouldRender(distance: Float, maxRenderDistance: Float = 500f): Boolean {
        return distance < maxRenderDistance
    }
}

class CullingSystem {
    fun isInFrustum(x: Float, y: Float, z: Float, radius: Float, cameraPos: Float): Boolean {
        val distance = sqrt(x * x + z * z)
        return distance < cameraPos + radius + 100f
    }
}
