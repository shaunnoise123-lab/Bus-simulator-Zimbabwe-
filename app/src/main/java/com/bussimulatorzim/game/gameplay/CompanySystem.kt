package com.bussimulatorzim.game.gameplay

import com.bussimulatorzim.game.vehicles.BusModel

class CompanySystem {
    private var playerCompany: BusCompany? = null
    private val aiCompanies = mutableListOf<BusCompany>()
    
    init {
        initializeAICompanies()
    }
    
    private fun initializeAICompanies() {
        aiCompanies.addAll(listOf(
            BusCompany(
                id = 1,
                name = "Pathetic Transport",
                reputation = 30f,
                busCount = 3,
                avgBusSpeed = 80f,
                avgBusCondition = 40f,
                routes = listOf("Harare-Bulawayo", "Harare-Mutare")
            ),
            BusCompany(
                id = 2,
                name = "Express Motors",
                reputation = 70f,
                busCount = 15,
                avgBusSpeed = 120f,
                avgBusCondition = 85f,
                routes = listOf("Harare-Bulawayo", "Harare-Mutare", "Harare-Masvingo")
            ),
            BusCompany(
                id = 3,
                name = "Sunrise Coaches",
                reputation = 60f,
                busCount = 8,
                avgBusSpeed = 110f,
                avgBusCondition = 75f,
                routes = listOf("Bulawayo-Victoria Falls", "Harare-Masvingo")
            ),
            BusCompany(
                id = 4,
                name = "Golden Highway",
                reputation = 50f,
                busCount = 5,
                avgBusSpeed = 100f,
                avgBusCondition = 60f,
                routes = listOf("Harare-Mutare", "Gweru-Bulawayo")
            )
        ))
    }
    
    fun createPlayerCompany(name: String): BusCompany {
        val company = BusCompany(
            id = 0,
            name = name,
            reputation = 30f,
            busCount = 1,
            avgBusSpeed = 80f,
            avgBusCondition = 100f
        )
        playerCompany = company
        return company
    }
    
    fun getPlayerCompany() = playerCompany
    fun getAICompanies() = aiCompanies
}

data class BusCompany(
    val id: Int,
    val name: String,
    var reputation: Float,
    var busCount: Int,
    var avgBusSpeed: Float,
    var avgBusCondition: Float,
    val buses: MutableList<BusModel> = mutableListOf(),
    var totalEarnings: Float = 0f,
    var totalExpenses: Float = 0f,
    val routes: List<String> = emptyList()
) {
    fun getProfit() = totalEarnings - totalExpenses
    
    fun addBus(bus: BusModel) {
        buses.add(bus)
        busCount = buses.size
    }
    
    fun removeBus(bus: BusModel) {
        buses.remove(bus)
        busCount = buses.size
    }
    
    fun updateReputation(change: Float) {
        reputation = (reputation + change).coerceIn(0f, 100f)
    }
}
