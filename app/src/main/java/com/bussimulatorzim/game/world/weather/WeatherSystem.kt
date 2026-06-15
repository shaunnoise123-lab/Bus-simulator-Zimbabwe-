package com.bussimulatorzim.game.world.weather

enum class WeatherCondition {
    CLEAR,
    RAINY,
    DUSTY,
    FLOODED,
    FOGGY,
    STORMY,
    SNOWY
}

data class WeatherState(
    val condition: WeatherCondition,
    val temperature: Float,
    val windSpeed: Float,
    val humidity: Float,
    val visibility: Float
)

class WeatherSystem {
    private var currentWeather = WeatherState(
        condition = WeatherCondition.CLEAR,
        temperature = 25f,
        windSpeed = 5f,
        humidity = 50f,
        visibility = 500f
    )
    
    private var weatherTimer = 0f
    private val weatherChangeInterval = 300f // 5 minutes
    
    fun initialize() {
        generateRandomWeather()
    }
    
    fun update(deltaTime: Float) {
        weatherTimer += deltaTime
        
        if (weatherTimer >= weatherChangeInterval) {
            generateRandomWeather()
            weatherTimer = 0f
        }
    }
    
    private fun generateRandomWeather() {
        val conditions = WeatherCondition.values()
        val randomCondition = conditions.random()
        
        currentWeather = when (randomCondition) {
            WeatherCondition.CLEAR -> WeatherState(
                condition = WeatherCondition.CLEAR,
                temperature = 25f,
                windSpeed = 5f,
                humidity = 50f,
                visibility = 500f
            )
            WeatherCondition.RAINY -> WeatherState(
                condition = WeatherCondition.RAINY,
                temperature = 18f,
                windSpeed = 15f,
                humidity = 90f,
                visibility = 200f
            )
            WeatherCondition.DUSTY -> WeatherState(
                condition = WeatherCondition.DUSTY,
                temperature = 32f,
                windSpeed = 25f,
                humidity = 20f,
                visibility = 150f
            )
            WeatherCondition.FLOODED -> WeatherState(
                condition = WeatherCondition.FLOODED,
                temperature = 20f,
                windSpeed = 20f,
                humidity = 95f,
                visibility = 100f
            )
            WeatherCondition.FOGGY -> WeatherState(
                condition = WeatherCondition.FOGGY,
                temperature = 15f,
                windSpeed = 2f,
                humidity = 85f,
                visibility = 50f
            )
            WeatherCondition.STORMY -> WeatherState(
                condition = WeatherCondition.STORMY,
                temperature = 22f,
                windSpeed = 40f,
                humidity = 100f,
                visibility = 80f
            )
            WeatherCondition.SNOWY -> WeatherState(
                condition = WeatherCondition.SNOWY,
                temperature = -5f,
                windSpeed = 30f,
                humidity = 80f,
                visibility = 100f
            )
        }
    }
    
    fun getCurrentWeather() = currentWeather
    fun setWeather(condition: WeatherCondition) {
        // Manual weather change
        generateRandomWeather()
    }
}
