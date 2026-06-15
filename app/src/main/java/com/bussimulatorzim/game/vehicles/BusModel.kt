package com.bussimulatorzim.game.vehicles

enum class BusType(val id: Int, val displayName: String, val price: Float) {
    // Classic Buses (Cheap)
    DAF_F218(1, "DAF F218 (Old)", 5000f),
    
    // Mid-range Buses
    ZHONG_TONG(2, "Zhong Tong", 25000f),
    HIGER(3, "Higer KLQ6", 28000f),
    
    // Premium Buses
    MARCOPOLO(4, "Marcopolo G8", 85000f),
    IRIZAR(5, "Irizar i8", 95000f),
    MAN(6, "MAN Lion's Star", 75000f),
    MERCEDES_BENZ(7, "Mercedes-Benz Tourismo", 120000f),
    
    // Luxury/Premium
    VOLVO(8, "Volvo B11R", 130000f),
    SCANIA(9, "Scania Metrobus", 140000f),
    
    // Budget variants
    ISUZU(10, "Isuzu Gala", 35000f),
    MITSUBISHI(11, "Mitsubishi Fuso", 40000f);
    
    companion object {
        fun getAllBuses() = values().toList()
    }
}

data class BusModel(
    val busType: BusType,
    val licensePlate: String,
    val purchaseDate: Long,
    val currentMileage: Float = 0f,
    val totalCapacity: Int = 50,
    val currentPassengers: Int = 0
) {
    var engineHealth = 100f
    var bodyHealth = 100f
    var windshieldHealth = 100f
    var tireCondition = floatArrayOf(100f, 100f, 100f, 100f, 100f, 100f)
    var fuelLevel = 100f
    var tirePressure = floatArrayOf(100f, 100f, 100f, 100f, 100f, 100f)
    var lastServiceDate = purchaseDate
    var totalServiceCost = 0f
    var totalFuelCost = 0f
    var totalRepairCost = 0f
    var totalEarnings = 0f
    
    fun needsService(): Boolean {
        val mileageSinceService = currentMileage - (lastServiceDate / 1000f)
        return mileageSinceService > 10000 || // Every 10,000 km
               engineHealth < 70 ||
               averageTireCondition() < 50
    }
    
    private fun averageTireCondition(): Float {
        return tireCondition.average().toFloat()
    }
    
    fun getHealthPercentage(): Float {
        return (engineHealth + bodyHealth + windshieldHealth) / 3
    }
    
    fun getRevenuePerPassenger(): Float {
        return when (busType) {
            BusType.DAF_F218 -> 0.5f
            BusType.ZHONG_TONG -> 1.2f
            BusType.HIGER -> 1.5f
            BusType.MARCOPOLO -> 2.0f
            BusType.IRIZAR -> 2.5f
            BusType.MAN -> 2.0f
            BusType.MERCEDES_BENZ -> 3.0f
            BusType.VOLVO -> 3.2f
            BusType.SCANIA -> 3.0f
            BusType.ISUZU -> 1.8f
            BusType.MITSUBISHI -> 1.9f
        }
    }
}
