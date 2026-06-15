package com.bussimulatorzim.game.cockpit

import com.bussimulatorzim.game.vehicles.BusModel

enum class GearMode {
    REVERSE,
    NEUTRAL,
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH
}

data class CockpitState(
    var engineRunning: Boolean = false,
    var headlights: Boolean = false,
    var highBeam: Boolean = false,
    var wipers: Boolean = false,
    var windersOpen: Boolean = false,
    var doorsOpen: Boolean = false,
    var currentGear: GearMode = GearMode.NEUTRAL,
    var steeringAngle: Float = 0f,
    var acceleratorPedal: Float = 0f,
    var brakePedal: Float = 0f,
    var hornActive: Boolean = false,
    var musicPlaying: Boolean = false,
    var musicVolume: Float = 0.5f,
    var cabinLightsOn: Boolean = true,
    var parkingBrake: Boolean = true
)

class CockpitController(val bus: BusModel) {
    var state = CockpitState()
    private var dashboardTemp = 0f
    private var engineTemp = 70f
    private var oilPressure = 100f
    private var batteryVoltage = 14f
    
    fun toggleEngine() {
        state.engineRunning = !state.engineRunning
        if (state.engineRunning) {
            engineTemp = 40f
            dashboardTemp = 25f
        } else {
            engineTemp = 0f
        }
    }
    
    fun toggleHeadlights() {
        state.headlights = !state.headlights
    }
    
    fun toggleHighBeam() {
        state.highBeam = !state.highBeam
    }
    
    fun toggleWipers() {
        state.wipers = !state.wipers
    }
    
    fun toggleWindows() {
        state.windersOpen = !state.windersOpen
    }
    
    fun toggleDoors() {
        state.doorsOpen = !state.doorsOpen
    }
    
    fun setGear(gear: GearMode) {
        state.currentGear = gear
    }
    
    fun setSteering(angle: Float) {
        state.steeringAngle = angle.coerceIn(-1f, 1f)
    }
    
    fun setAccelerator(pedal: Float) {
        state.acceleratorPedal = pedal.coerceIn(0f, 1f)
    }
    
    fun setBrake(pedal: Float) {
        state.brakePedal = pedal.coerceIn(0f, 1f)
    }
    
    fun pressHorn() {
        state.hornActive = true
    }
    
    fun releaseHorn() {
        state.hornActive = false
    }
    
    fun toggleMusic() {
        state.musicPlaying = !state.musicPlaying
    }
    
    fun setMusicVolume(volume: Float) {
        state.musicVolume = volume.coerceIn(0f, 1f)
    }
    
    fun toggleCabinLights() {
        state.cabinLightsOn = !state.cabinLightsOn
    }
    
    fun setParkingBrake(active: Boolean) {
        state.parkingBrake = active
    }
    
    fun update(deltaTime: Float) {
        if (state.engineRunning) {
            engineTemp += state.acceleratorPedal * 5f * deltaTime
            engineTemp = engineTemp.coerceIn(40f, 120f)
            
            oilPressure = (80f + state.acceleratorPedal * 30f).coerceIn(0f, 150f)
            
            batteryVoltage = 14f - (state.musicPlaying.compareTo(false) * 0.1f)
        }
    }
    
    fun getEngineTemp() = engineTemp
    fun getOilPressure() = oilPressure
    fun getBatteryVoltage() = batteryVoltage
    fun getDashboardTemp() = dashboardTemp
    
    fun getGearDisplay(): String {
        return when (state.currentGear) {
            GearMode.REVERSE -> "R"
            GearMode.NEUTRAL -> "N"
            GearMode.FIRST -> "1"
            GearMode.SECOND -> "2"
            GearMode.THIRD -> "3"
            GearMode.FOURTH -> "4"
            GearMode.FIFTH -> "5"
            GearMode.SIXTH -> "6"
        }
    }
}
