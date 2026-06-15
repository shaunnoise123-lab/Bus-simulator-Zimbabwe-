package com.bussimulatorzim.game.core

import android.content.Context
import com.bussimulatorzim.game.physics.PhysicsEngine
import com.bussimulatorzim.game.rendering.RenderEngine
import com.bussimulatorzim.game.world.WorldManager
import com.bussimulatorzim.game.traffic.TrafficManager
import com.bussimulatorzim.game.economy.EconomyManager
import kotlinx.coroutines.Job

class GameEngine(context: Context) {
    private val worldManager = WorldManager(context)
    private val physicsEngine = PhysicsEngine()
    private val renderEngine = RenderEngine()
    private val trafficManager = TrafficManager()
    private val economyManager = EconomyManager(context)
    
    var isRunning = false
    var gameTimeMultiplier = 3f // 3 real hours = 24 game hours
    var gameDeltaTime = 0f
    
    private var gameLoopJob: Job? = null
    
    fun initialize() {
        worldManager.initialize()
        economyManager.initialize()
        trafficManager.initialize()
    }
    
    fun start() {
        isRunning = true
        gameLoopJob = startGameLoop()
    }
    
    fun pause() {
        isRunning = false
    }
    
    fun resume() {
        isRunning = true
    }
    
    fun update(deltaTime: Float) {
        if (!isRunning) return
        
        gameDeltaTime = deltaTime
        
        // Update world time
        worldManager.updateTime(deltaTime * gameTimeMultiplier)
        
        // Update physics
        physicsEngine.update(deltaTime)
        
        // Update traffic
        trafficManager.update(deltaTime)
        
        // Update economy
        economyManager.update(deltaTime)
    }
    
    fun render() {
        if (!isRunning) return
        renderEngine.render(worldManager, physicsEngine, trafficManager)
    }
    
    fun shutdown() {
        isRunning = false
        gameLoopJob?.cancel()
        worldManager.cleanup()
    }
    
    private fun startGameLoop(): Job? {
        // Game loop implementation
        return null
    }
    
    // Getters
    fun getWorldManager() = worldManager
    fun getPhysicsEngine() = physicsEngine
    fun getRenderEngine() = renderEngine
    fun getTrafficManager() = trafficManager
    fun getEconomyManager() = economyManager
}
