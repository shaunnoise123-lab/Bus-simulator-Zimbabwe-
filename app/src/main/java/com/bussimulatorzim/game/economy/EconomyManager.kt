package com.bussimulatorzim.game.economy

import android.content.Context
import androidx.room.Room
import com.bussimulatorzim.game.data.GameDatabase
import com.bussimulatorzim.game.vehicles.BusModel
import com.bussimulatorzim.game.vehicles.BusType

data class PlayerProfile(
    val playerName: String,
    var usdBalance: Float = 1000f,
    var zigBalance: Float = 5000f,
    var reputation: Float = 50f,
    var currentCompany: String = "Beginner Transport",
    var ownedBuses: MutableList<BusModel> = mutableListOf(),
    var totalMileage: Float = 0f,
    var totalPassengers: Int = 0,
    var totalTrips: Int = 0,
    var level: Int = 1,
    var xp: Int = 0
)

enum class Currency {
    USD,
    ZIG  // Zimbabwe Gold
}

class EconomyManager(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        GameDatabase::class.java,
        "bus_simulator_db"
    ).build()
    
    private var playerProfile = PlayerProfile("Player")
    
    fun initialize() {
        // Load player profile from database
    }
    
    fun update(deltaTime: Float) {
        // Update economy state
        // Update fuel consumption, maintenance costs, etc.
    }
    
    // Money Management
    fun addMoney(amount: Float, currency: Currency) {
        when (currency) {
            Currency.USD -> playerProfile.usdBalance += amount
            Currency.ZIG -> playerProfile.zigBalance += amount
        }
    }
    
    fun spendMoney(amount: Float, currency: Currency): Boolean {
        return when (currency) {
            Currency.USD -> {
                if (playerProfile.usdBalance >= amount) {
                    playerProfile.usdBalance -= amount
                    true
                } else false
            }
            Currency.ZIG -> {
                if (playerProfile.zigBalance >= amount) {
                    playerProfile.zigBalance -= amount
                    true
                } else false
            }
        }
    }
    
    fun convertCurrency(amount: Float, from: Currency, to: Currency): Float {
        val exchangeRate = 0.0031f // 1 ZIG = 0.0031 USD (approximate)
        return when {
            from == Currency.ZIG && to == Currency.USD -> amount * exchangeRate
            from == Currency.USD && to == Currency.ZIG -> amount / exchangeRate
            else -> amount
        }
    }
    
    // Bus Management
    fun buyBus(busType: BusType): Boolean {
        if (playerProfile.usdBalance >= busType.price) {
            spendMoney(busType.price, Currency.USD)
            val bus = BusModel(
                busType = busType,
                licensePlate = generateLicensePlate(),
                purchaseDate = System.currentTimeMillis()
            )
            playerProfile.ownedBuses.add(bus)
            return true
        }
        return false
    }
    
    fun sellBus(bus: BusModel): Float {
        val sellPrice = bus.busType.price * (bus.getHealthPercentage() / 100)
        playerProfile.ownedBuses.remove(bus)
        addMoney(sellPrice, Currency.USD)
        return sellPrice
    }
    
    // Reputation System
    fun increaseReputation(amount: Float) {
        playerProfile.reputation = (playerProfile.reputation + amount).coerceIn(0f, 100f)
        updateLevel()
    }
    
    fun decreaseReputation(amount: Float) {
        playerProfile.reputation = (playerProfile.reputation - amount).coerceIn(0f, 100f)
    }
    
    private fun updateLevel() {
        val newLevel = (playerProfile.reputation / 20).toInt() + 1
        if (newLevel > playerProfile.level) {
            playerProfile.level = newLevel
        }
    }
    
    fun getPlayerProfile() = playerProfile
    
    private fun generateLicensePlate(): String {
        val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        var plate = ""
        repeat(2) { plate += letters.random() }
        repeat(3) { plate += numbers.random() }
        plate += letters.random()
        return plate
    }
}
