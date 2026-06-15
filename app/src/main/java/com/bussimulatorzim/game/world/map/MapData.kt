package com.bussimulatorzim.game.world.map

import android.content.Context

data class Road(
    val name: String,
    val startCity: String,
    val endCity: String,
    val length: Float, // km
    val type: RoadType,
    val difficulty: Int // 1-5
)

enum class RoadType {
    MAIN_HIGHWAY,
    DUSTY_VILLAGE,
    OFF_ROAD,
    DAMAGED,
    DANGEROUS
}

class MapData(context: Context) {
    private val roads = mutableListOf<Road>()
    private val cities = mutableListOf<City>()
    
    fun loadMap() {
        loadRoads()
        loadCities()
    }
    
    private fun loadRoads() {
        roads.addAll(listOf(
            Road("A5 Highway", "Harare", "Bulawayo", 450f, RoadType.MAIN_HIGHWAY, 1),
            Road("A3 Highway", "Harare", "Mutare", 280f, RoadType.MAIN_HIGHWAY, 2),
            Road("A4 Highway", "Harare", "Masvingo", 300f, RoadType.MAIN_HIGHWAY, 2),
            Road("A8 Safari Route", "Bulawayo", "Victoria Falls", 350f, RoadType.DAMAGED, 4),
            Road("Mbizi Road", "Harare", "Mbizi", 120f, RoadType.DUSTY_VILLAGE, 3),
            Road("Nyanga Road", "Mutare", "Nyanga", 80f, RoadType.DANGEROUS, 5),
            Road("Kariba Road", "Chinhoyi", "Kariba", 180f, RoadType.DUSTY_VILLAGE, 4)
        ))
    }
    
    private fun loadCities() {
        cities.addAll(listOf(
            City("Harare", 0f, 0f, true),
            City("Bulawayo", -200f, 0f, true),
            City("Mutare", 100f, 150f, true),
            City("Masvingo", -50f, -200f, true),
            City("Victoria Falls", -280f, 80f, false),
            City("Gweru", -120f, -80f, true),
            City("Kwekwe", -80f, -40f, true),
            City("Kadoma", -30f, 20f, true),
            City("Chinhoyi", 40f, 80f, true),
            City("Beitbridge", -100f, -400f, false)
        ))
    }
    
    fun getRoads() = roads
    fun getCities() = cities
    
    fun unload() {
        roads.clear()
        cities.clear()
    }
}

data class City(
    val name: String,
    val x: Float,
    val y: Float,
    val isMainCity: Boolean
)
