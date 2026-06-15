package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val type: EventType,
    val reward: Float = 0f,
    val penalty: Float = 0f,
    var isActive: Boolean = true
)

enum class EventType {
    ROAD_CONSTRUCTION,
    ACCIDENT,
    POLICE_CHASE,
    VEHICLE_BREAKDOWN,
    PASSENGER_EMERGENCY,
    WEATHER_ALERT,
    MAINTENANCE_REQUIRED
}

class EventSystem {
    private val events = mutableListOf<Event>()
    private var eventIdCounter = 0
    private val eventProbability = 0.0005f
    
    fun update(deltaTime: Float) {
        // Random event generation
        if (Math.random() < eventProbability) {
            generateRandomEvent()
        }
        
        // Remove completed events
        events.removeAll { !it.isActive }
    }
    
    private fun generateRandomEvent() {
        val eventTypes = EventType.values()
        val type = eventTypes.random()
        
        val event = when (type) {
            EventType.ROAD_CONSTRUCTION -> Event(
                id = eventIdCounter++,
                title = "Road Construction Ahead",
                description = "Speed limited to 40 km/h",
                type = type,
                penalty = 0f
            )
            EventType.ACCIDENT -> Event(
                id = eventIdCounter++,
                title = "Accident Reported",
                description = "Use alternate route",
                type = type,
                penalty = 0f
            )
            EventType.POLICE_CHASE -> Event(
                id = eventIdCounter++,
                title = "Police Chase!",
                description = "Evade or surrender",
                type = type,
                penalty = 0f
            )
            EventType.VEHICLE_BREAKDOWN -> Event(
                id = eventIdCounter++,
                title = "Vehicle Breakdown",
                description = "Engine needs repair",
                type = type,
                penalty = 0f
            )
            EventType.PASSENGER_EMERGENCY -> Event(
                id = eventIdCounter++,
                title = "Passenger Emergency",
                description = "Take patient to hospital",
                type = type,
                reward = 500f
            )
            EventType.WEATHER_ALERT -> Event(
                id = eventIdCounter++,
                title = "Severe Weather",
                description = "Reduce speed and increase caution",
                type = type,
                penalty = 0f
            )
            EventType.MAINTENANCE_REQUIRED -> Event(
                id = eventIdCounter++,
                title = "Maintenance Due",
                description = "Visit service station",
                type = type,
                penalty = -500f
            )
        }
        
        events.add(event)
    }
    
    fun completeEvent(eventId: Int) {
        events.find { it.id == eventId }?.isActive = false
    }
    
    fun getActiveEvents() = events.filter { it.isActive }
    fun getAllEvents() = events
}
