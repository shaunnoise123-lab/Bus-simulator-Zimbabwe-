package com.bussimulatorzim.game.gameplay

data class Passenger(
    val id: Int,
    val name: String,
    val mood: Float = 50f,
    val destination: String,
    val boardingTime: Long = System.currentTimeMillis(),
    var isOnBus: Boolean = false
)

class PassengerSystem {
    private val passengers = mutableListOf<Passenger>()
    private var passengerIdCounter = 0
    
    fun boardPassengers(busCapacity: Int, destination: String, basefare: Float): List<Passenger> {
        val boardingPassengers = mutableListOf<Passenger>()
        val randomCount = (Math.random() * (busCapacity * 0.8)).toInt()
        
        repeat(randomCount) {
            val passenger = Passenger(
                id = passengerIdCounter++,
                name = generatePassengerName(),
                mood = 50f + (Math.random() * 30f).toFloat(),
                destination = destination,
                isOnBus = true
            )
            boardingPassengers.add(passenger)
            passengers.add(passenger)
        }
        
        return boardingPassengers
    }
    
    fun updatePassengerMood(deltaTime: Float) {
        for (passenger in passengers) {
            if (passenger.isOnBus) {
                passenger.mood = (passenger.mood - 0.1f * deltaTime).coerceIn(0f, 100f)
            }
        }
    }
    
    fun dropOffPassengers(destination: String): Int {
        val droppingOff = passengers.filter { it.destination == destination && it.isOnBus }
        droppingOff.forEach { it.isOnBus = false }
        passengers.removeAll(droppingOff)
        return droppingOff.size
    }
    
    fun getPassengerCount(): Int = passengers.count { it.isOnBus }
    fun getAverageMood(): Float = if (passengers.isEmpty()) 50f else passengers.averageOf { it.mood.toDouble() }.toFloat()
    
    private fun generatePassengerName(): String {
        val names = listOf(
            "Tendai", "Amara", "Zweli", "Chipo", "Bongani",
            "Nomsa", "Thabo", "Lindiwe", "Mvula", "Adanna"
        )
        return names.random()
    }
}
