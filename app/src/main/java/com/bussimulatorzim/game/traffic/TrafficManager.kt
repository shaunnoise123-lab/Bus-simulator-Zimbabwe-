package com.bussimulatorzim.game.traffic

data class TrafficRules(
    val speedLimit: Int = 120, // km/h
    val trafficLightDuration: Float = 30f, // seconds
    val roadBlockSearchChance: Float = 0.3f // 30% chance
)

enum class TrafficViolationType {
    SPEEDING,
    RED_LIGHT,
    ILLEGAL_PARKING,
    RECKLESS_DRIVING,
    CARRYING_ILLEGAL_GOODS
}

data class Ticket(
    val violationType: TrafficViolationType,
    val fine: Float,
    val issueTime: Long
)

class TrafficManager {
    private val rules = TrafficRules()
    private val violations = mutableListOf<Ticket>()
    private val activeTrafficLights = mutableListOf<TrafficLight>()
    
    fun initialize() {
        // Initialize traffic lights at major intersections
    }
    
    fun update(deltaTime: Float) {
        // Update traffic lights
        for (light in activeTrafficLights) {
            light.update(deltaTime)
        }
    }
    
    fun issueTicket(violationType: TrafficViolationType): Ticket {
        val fine = when (violationType) {
            TrafficViolationType.SPEEDING -> 50f
            TrafficViolationType.RED_LIGHT -> 75f
            TrafficViolationType.ILLEGAL_PARKING -> 25f
            TrafficViolationType.RECKLESS_DRIVING -> 150f
            TrafficViolationType.CARRYING_ILLEGAL_GOODS -> 200f
        }
        
        val ticket = Ticket(violationType, fine, System.currentTimeMillis())
        violations.add(ticket)
        return ticket
    }
    
    fun getTotalFines(): Float = violations.sumOf { it.fine.toDouble() }.toFloat()
    fun getViolations() = violations
}

data class TrafficLight(
    val x: Float,
    val y: Float
) {
    enum class State { RED, AMBER, GREEN }
    
    var currentState = State.RED
    var stateTimer = 0f
    var redDuration = 30f
    var greenDuration = 25f
    var amberDuration = 5f
    
    fun update(deltaTime: Float) {
        stateTimer += deltaTime
        
        when (currentState) {
            State.RED -> {
                if (stateTimer >= redDuration) {
                    currentState = State.GREEN
                    stateTimer = 0f
                }
            }
            State.GREEN -> {
                if (stateTimer >= greenDuration) {
                    currentState = State.AMBER
                    stateTimer = 0f
                }
            }
            State.AMBER -> {
                if (stateTimer >= amberDuration) {
                    currentState = State.RED
                    stateTimer = 0f
                }
            }
        }
    }
}
