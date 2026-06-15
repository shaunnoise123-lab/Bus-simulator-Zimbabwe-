package com.bussimulatorzim.game.gameplay

data class Thief(
    val id: Int,
    val name: String,
    var isActive: Boolean = false
)

class NightThiefSystem {
    private val thieves = mutableListOf<Thief>()
    private var thievesSpawned = 0
    private val maxThieves = 5
    private val thievesPerNight = 2
    private var lastThievesSpawnTime = System.currentTimeMillis()
    
    fun update(isNight: Boolean, playerBankBalance: Float) {
        if (!isNight) return
        
        val timeSinceLastSpawn = System.currentTimeMillis() - lastThievesSpawnTime
        
        // Spawn new thieves every 2 minutes
        if (timeSinceLastSpawn > 120000 && thievesSpawned < thievesPerNight) {
            spawnThieves()
            lastThievesSpawnTime = System.currentTimeMillis()
        }
    }
    
    private fun spawnThieves() {
        repeat(thievesPerNight.coerceAtMost(maxThieves - thievesSpawned)) {
            val thief = Thief(
                id = thievesSpawned++,
                name = generateThiefName(),
                isActive = true
            )
            thieves.add(thief)
        }
    }
    
    fun attemptTheft(playerBankBalance: Float): Float {
        if (!thieves.any { it.isActive }) return 0f
        
        val stolenAmount = playerBankBalance * 0.5f
        thieves.forEach { it.isActive = false }
        return stolenAmount
    }
    
    private fun generateThiefName(): String {
        val names = listOf("Ghost", "Shadow", "Blade", "Viper", "Smoke")
        return names.random()
    }
    
    fun getActiveThieves() = thieves.filter { it.isActive }
}
