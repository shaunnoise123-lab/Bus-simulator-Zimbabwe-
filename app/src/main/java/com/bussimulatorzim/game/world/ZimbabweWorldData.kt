package com.bussimulatorzim.game.world

import com.bussimulatorzim.game.physics.Vector3

data class ZimbabweCity(
    val name: String,
    val position: Vector3,
    val population: Int,
    val isCapital: Boolean,
    val routes: List<String> = emptyList()
)

data class ZimbabweVillage(
    val name: String,
    val position: Vector3,
    val population: Int,
    val type: VillageType // GROWTH_POINT, FARMING, MINING, etc.
)

enum class VillageType {
    GROWTH_POINT,
    FARMING_COMMUNITY,
    MINING_TOWN,
    BORDER_TOWN,
    RURAL_SETTLEMENT,
    TRADING_POST
}

data class RouteSegment(
    val startCity: String,
    val endCity: String,
    val distance: Float, // km
    val averageTime: Float, // minutes
    val roadCondition: RoadCondition,
    val dangerLevel: Int // 1-5
)

enum class RoadCondition {
    EXCELLENT,  // New asphalt
    GOOD,       // Normal highway
    FAIR,       // Some potholes
    POOR,       // Damaged, dusty
    DANGEROUS   // Off-road, flooded, etc.
}

class ZimbabweWorldData {
    val cities = mutableListOf<ZimbabweCity>()
    val villages = mutableListOf<ZimbabweVillage>()
    val routes = mutableListOf<RouteSegment>()
    
    fun initialize() {
        initializeCities()
        initializeVillages()
        initializeRoutes()
    }
    
    private fun initializeCities() {
        cities.addAll(listOf(
            ZimbabweCity("Harare", Vector3(0f, 0f, 0f), 1600000, true, listOf("Bulawayo", "Mutare", "Masvingo")),
            ZimbabweCity("Bulawayo", Vector3(-200f, 0f, 0f), 650000, false, listOf("Harare", "Victoria Falls", "Hwange")),
            ZimbabweCity("Mutare", Vector3(100f, 150f, 0f), 200000, false, listOf("Harare", "Chimanimani")),
            ZimbabweCity("Masvingo", Vector3(-50f, -200f, 0f), 100000, false, listOf("Harare", "Beitbridge")),
            ZimbabweCity("Gweru", Vector3(-120f, -80f, 0f), 150000, false, listOf("Harare", "Bulawayo")),
            ZimbabweCity("Kwekwe", Vector3(-80f, -40f, 0f), 80000, false, listOf("Harare", "Gweru")),
            ZimbabweCity("Victoria Falls", Vector3(-280f, 80f, 0f), 60000, false, listOf("Bulawayo", "Livingstone")),
            ZimbabweCity("Chinhoyi", Vector3(40f, 80f, 0f), 50000, false, listOf("Harare", "Kariba")),
            ZimbabweCity("Kadoma", Vector3(-30f, 20f, 0f), 75000, false, listOf("Harare", "Gweru")),
            ZimbabweCity("Beitbridge", Vector3(-100f, -400f, 0f), 40000, false, listOf("Masvingo", "South Africa"))
        ))
    }
    
    private fun initializeVillages() {
        villages.addAll(listOf(
            ZimbabweVillage("Mbare", Vector3(10f, 10f, 0f), 50000, VillageType.GROWTH_POINT),
            ZimbabweVillage("Highfield", Vector3(15f, 20f, 0f), 30000, VillageType.GROWTH_POINT),
            ZimbabweVillage("Chitungwiza", Vector3(5f, -30f, 0f), 330000, VillageType.GROWTH_POINT),
            ZimbabweVillage("Waterfalls", Vector3(-150f, 50f, 0f), 20000, VillageType.FARMING_COMMUNITY),
            ZimbabweVillage("Shamva", Vector3(80f, 60f, 0f), 15000, VillageType.MINING_TOWN),
            ZimbabweVillage("Chegutu", Vector3(-60f, 30f, 0f), 18000, VillageType.FARMING_COMMUNITY),
            ZimbabweVillage("Karoi", Vector3(60f, 100f, 0f), 12000, VillageType.RURAL_SETTLEMENT),
            ZimbabweVillage("Banket", Vector3(-90f, 70f, 0f), 10000, VillageType.FARMING_COMMUNITY),
            ZimbabweVillage("Mvuma", Vector3(-30f, -120f, 0f), 8000, VillageType.MINING_TOWN),
            ZimbabweVillage("Zvishavane", Vector3(-40f, -180f, 0f), 25000, VillageType.MINING_TOWN),
            ZimbabweVillage("Gutu", Vector3(0f, -220f, 0f), 5000, VillageType.RURAL_SETTLEMENT),
            ZimbabweVillage("Chiredzi", Vector3(80f, -280f, 0f), 20000, VillageType.FARMING_COMMUNITY),
            ZimbabweVillage("Buhera", Vector3(120f, -150f, 0f), 10000, VillageType.RURAL_SETTLEMENT),
            ZimbabweVillage("Chimanimani", Vector3(150f, 120f, 0f), 8000, VillageType.BORDER_TOWN),
            ZimbabweVillage("Nyanga", Vector3(140f, 180f, 0f), 12000, VillageType.FARMING_COMMUNITY)
        ))
    }
    
    private fun initializeRoutes() {
        routes.addAll(listOf(
            RouteSegment("Harare", "Bulawayo", 450f, 380f, RoadCondition.GOOD, 2),
            RouteSegment("Harare", "Mutare", 280f, 240f, RoadCondition.FAIR, 2),
            RouteSegment("Harare", "Masvingo", 300f, 260f, RoadCondition.GOOD, 2),
            RouteSegment("Bulawayo", "Victoria Falls", 350f, 300f, RoadCondition.POOR, 4),
            RouteSegment("Harare", "Chinhoyi", 100f, 90f, RoadCondition.GOOD, 1),
            RouteSegment("Mutare", "Chimanimani", 80f, 70f, RoadCondition.POOR, 3),
            RouteSegment("Harare", "Gweru", 220f, 200f, RoadCondition.GOOD, 1),
            RouteSegment("Gweru", "Bulawayo", 230f, 210f, RoadCondition.GOOD, 1),
            RouteSegment("Harare", "Kadoma", 140f, 120f, RoadCondition.GOOD, 1),
            RouteSegment("Masvingo", "Beitbridge", 280f, 240f, RoadCondition.FAIR, 2)
        ))
    }
    
    fun getCityByName(name: String): ZimbabweCity? = cities.find { it.name == name }
    fun getVillageByName(name: String): ZimbabweVillage? = villages.find { it.name == name }
    fun getRouteByDestination(start: String, end: String): RouteSegment? {
        return routes.find { (it.startCity == start && it.endCity == end) || (it.startCity == end && it.endCity == start) }
    }
}
