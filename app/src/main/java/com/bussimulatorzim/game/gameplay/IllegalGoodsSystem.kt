package com.bussimulatorzim.game.gameplay

enum class IllegalGood {
    DIAMONDS,
    WILDLIFE,
    TOBACCO,
    ALCOHOL,
    FUEL_BLACK_MARKET,
    CURRENCY
}

data class IllegalGoodLoad(
    val good: IllegalGood,
    val quantity: Int,
    val value: Float,
    val riskLevel: Float
)

class IllegalGoodsSystem {
    private val cargoLoaded = mutableListOf<IllegalGoodLoad>()
    private var totalValue = 0f
    
    fun loadIllegalGoods(good: IllegalGood, quantity: Int): Float {
        val baseValue = when (good) {
            IllegalGood.DIAMONDS -> 5000f
            IllegalGood.WILDLIFE -> 4000f
            IllegalGood.TOBACCO -> 1500f
            IllegalGood.ALCOHOL -> 2000f
            IllegalGood.FUEL_BLACK_MARKET -> 1000f
            IllegalGood.CURRENCY -> 3000f
        }
        
        val value = baseValue * quantity
        val riskLevel = when (good) {
            IllegalGood.DIAMONDS -> 0.9f
            IllegalGood.WILDLIFE -> 0.95f
            IllegalGood.TOBACCO -> 0.3f
            IllegalGood.ALCOHOL -> 0.5f
            IllegalGood.FUEL_BLACK_MARKET -> 0.4f
            IllegalGood.CURRENCY -> 0.8f
        }
        
        val load = IllegalGoodLoad(good, quantity, value, riskLevel)
        cargoLoaded.add(load)
        totalValue += value
        
        return value
    }
    
    fun unloadIllegalGoods(): Float {
        val value = totalValue
        cargoLoaded.clear()
        totalValue = 0f
        return value
    }
    
    fun hasIllegalGoods(): Boolean = cargoLoaded.isNotEmpty()
    fun getTotalValue() = totalValue
    fun getCargoLoaded() = cargoLoaded
    fun getMaxRiskLevel() = cargoLoaded.maxOfOrNull { it.riskLevel } ?: 0f
}
