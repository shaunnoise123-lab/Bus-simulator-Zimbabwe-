package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.physics.Vector3

data class FuelStation(
    val id: Int,
    val name: String,
    val position: Vector3,
    val pricePerLiter: Float,
    val fuelType: FuelType = FuelType.DIESEL
)

enum class FuelType {
    PETROL,
    DIESEL,
    LPG
}

data class ServiceStation(
    val id: Int,
    val name: String,
    val position: Vector3,
    val services: List<ServiceType> = listOf(ServiceType.TIRE_CHANGE, ServiceType.ENGINE_REPAIR),
    val costMultiplier: Float = 1f
)

enum class ServiceType {
    TIRE_CHANGE,
    ENGINE_REPAIR,
    BODY_REPAIR,
    PAINT_JOB,
    WINDSHIELD_REPLACEMENT,
    FULL_SERVICE,
    DIAGNOSTIC
}

data class RestArea(
    val id: Int,
    val name: String,
    val position: Vector3,
    val facilities: List<Facility>
)

enum class Facility {
    TOILET,
    RESTAURANT,
    SHOP,
    PARKING,
    PHONE_CHARGING,
    ACCOMMODATION
}

class InfrastructureSystem {
    private val fuelStations = mutableListOf<FuelStation>()
    private val serviceStations = mutableListOf<ServiceStation>()
    private val restAreas = mutableListOf<RestArea>()
    private var stationIdCounter = 0
    
    fun initialize() {
        initializeFuelStations()
        initializeServiceStations()
        initializeRestAreas()
    }
    
    private fun initializeFuelStations() {
        fuelStations.addAll(listOf(
            FuelStation(stationIdCounter++, "Total Harare", Vector3(5f, 5f, 0f), 1.50f),
            FuelStation(stationIdCounter++, "Puma Bulawayo", Vector3(-200f, 0f, 0f), 1.48f),
            FuelStation(stationIdCounter++, "Shell Mutare", Vector3(100f, 150f, 0f), 1.60f),
            FuelStation(stationIdCounter++, "Caltex Masvingo", Vector3(-50f, -200f, 0f), 1.45f),
            FuelStation(stationIdCounter++, "Engen Gweru", Vector3(-120f, -80f, 0f), 1.50f)
        ))
    }
    
    private fun initializeServiceStations() {
        serviceStations.addAll(listOf(
            ServiceStation(
                stationIdCounter++,
                "Chivhu Mechanics",
                Vector3(20f, 20f, 0f),
                listOf(ServiceType.TIRE_CHANGE, ServiceType.ENGINE_REPAIR, ServiceType.BODY_REPAIR),
                1.0f
            ),
            ServiceStation(
                stationIdCounter++,
                "Harare Central Garage",
                Vector3(10f, 10f, 0f),
                listOf(ServiceType.FULL_SERVICE, ServiceType.DIAGNOSTIC),
                1.2f
            ),
            ServiceStation(
                stationIdCounter++,
                "Bulawayo Bus Depot",
                Vector3(-200f, 5f, 0f),
                listOf(ServiceType.TIRE_CHANGE, ServiceType.ENGINE_REPAIR),
                0.9f
            )
        ))
    }
    
    private fun initializeRestAreas() {
        restAreas.addAll(listOf(
            RestArea(
                stationIdCounter++,
                "A5 Highway Rest Stop",
                Vector3(-100f, 0f, 0f),
                listOf(Facility.TOILET, Facility.RESTAURANT, Facility.PARKING)
            ),
            RestArea(
                stationIdCounter++,
                "Mutare Market Area",
                Vector3(100f, 150f, 0f),
                listOf(Facility.SHOP, Facility.RESTAURANT, Facility.PHONE_CHARGING)
            )
        ))
    }
    
    fun getNearestFuelStation(position: Vector3, range: Float = 200f): FuelStation? {
        return fuelStations.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            kotlin.math.sqrt(dx * dx + dz * dz) <= range
        }
    }
    
    fun getNearestServiceStation(position: Vector3, range: Float = 200f): ServiceStation? {
        return serviceStations.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            kotlin.math.sqrt(dx * dx + dz * dz) <= range
        }
    }
    
    fun getNearestRestArea(position: Vector3, range: Float = 200f): RestArea? {
        return restAreas.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            kotlin.math.sqrt(dx * dx + dz * dz) <= range
        }
    }
    
    fun getFuelStations() = fuelStations
    fun getServiceStations() = serviceStations
    fun getRestAreas() = restAreas
}
