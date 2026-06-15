package com.bussimulatorzim.game.gameplay

class TimeSystem {
    private var gameTimeSeconds = 0f
    private var realTimeMultiplier = 3f
    
    fun update(deltaTimeSeconds: Float) {
        gameTimeSeconds += deltaTimeSeconds * realTimeMultiplier
    }
    
    fun getGameTimeSeconds(): Float = gameTimeSeconds
    fun getGameTimeMinutes(): Float = gameTimeSeconds / 60f
    fun getGameTimeHours(): Float = gameTimeSeconds / 3600f
    fun getGameTimeDays(): Float = gameTimeSeconds / 86400f
    
    fun getFormattedTime(): String {
        val hours = (getGameTimeHours().toInt() % 24)
        val minutes = ((getGameTimeMinutes() % 60).toInt())
        return String.format("%02d:%02d", hours, minutes)
    }
    
    fun isNight(): Boolean {
        val hours = getGameTimeHours().toInt() % 24
        return hours < 6 || hours >= 18
    }
    
    fun isDay(): Boolean = !isNight()
    fun setTimeMultiplier(multiplier: Float) {
        realTimeMultiplier = multiplier
    }
    
    fun reset() {
        gameTimeSeconds = 0f
    }
}
