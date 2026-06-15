package com.bussimulatorzim.game.world

import android.content.Context
import com.bussimulatorzim.game.world.weather.WeatherSystem
import com.bussimulatorzim.game.world.map.MapData
import kotlin.math.floor

class WorldManager(context: Context) {
    private val mapData = MapData(context)
    private val weatherSystem = WeatherSystem()
    
    // Time system
    private var gameTimeHours = 0f
    private var gameTimeMinutes = 0f
    private var isDay = true
    
    // Zimbabwe locations
    private val cities = listOf(
        "Harare", "Bulawayo", "Mutare", "Masvingo",
        "Gweru", "Kwekwe", "Kadoma", "Chinhoyi",
        "Victoria Falls", "Beitbridge"
    )
    
    private val villages = mutableListOf<String>()
    
    fun initialize() {
        mapData.loadMap()
        weatherSystem.initialize()
        populateVillages()
    }
    
    fun updateTime(deltaTime: Float) {
        gameTimeMinutes += deltaTime // deltaTime in seconds
        
        if (gameTimeMinutes >= 60) {
            gameTimeHours += gameTimeMinutes / 60
            gameTimeMinutes %= 60
        }
        
        if (gameTimeHours >= 24) {
            gameTimeHours = 0f
        }
        
        // Update day/night cycle
        isDay = gameTimeHours in 6f..18f
        
        // Update weather
        weatherSystem.update(deltaTime)
    }
    
    fun getGameTime(): Pair<Float, Float> = Pair(gameTimeHours, gameTimeMinutes)
    fun isGameDay() = isDay
    fun getWeather() = weatherSystem.getCurrentWeather()
    fun getMapData() = mapData
    
    private fun populateVillages() {
        val villageNames = arrayOf(
            "Mbare", "Highfield", "Mufakose", "Mabvuku",
            "Chitungwiza", "Waterfalls", "Chegutu", "Karoi",
            "Banket", "Shamva", "Mvuma", "Zvishavane",
            "Gutu", "Chiredzi", "Buhera", "Chimanimani",
            "Birchenough", "Nyazura", "Mutoko", "Mbizi"
        )
        villages.addAll(villageNames)
    }
    
    fun cleanup() {
        mapData.unload()
    }
}
