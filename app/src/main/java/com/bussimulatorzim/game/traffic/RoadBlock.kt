package com.bussimulatorzim.game.traffic

import com.bussimulatorzim.game.physics.Vector3

enum class RoadBlockType {
    POLICE_CHECKPOINT,
    ZINARA_TOLL_GATE,
    MILITARY_CHECKPOINT,
    WILDLIFE_GATE
}

enum class SearchResult {
    CLEAN,
    ILLEGAL_GOODS_FOUND,
    DOCUMENT_ISSUE,
    VEHICLE_PROBLEM
}

data class RoadBlock(
    val position: Vector3,
    val type: RoadBlockType,
    val name: String,
    val fine: Float = 100f,
    val searchChance: Float = 0.3f,
    var isActive: Boolean = true
) {
    var officersPresent = 3
    var lastPlayerStop: Long = 0L
    
    fun searchBus(hasIllegalGoods: Boolean): SearchResult {
        return when {
            hasIllegalGoods && Math.random() < searchChance -> SearchResult.ILLEGAL_GOODS_FOUND
            Math.random() < 0.2f -> SearchResult.DOCUMENT_ISSUE
            Math.random() < 0.1f -> SearchResult.VEHICLE_PROBLEM
            else -> SearchResult.CLEAN
        }
    }
}

class RoadBlockManager {
    private val roadBlocks = mutableListOf<RoadBlock>()
    
    fun initialize() {
        roadBlocks.addAll(listOf(
            RoadBlock(
                position = Vector3(-100f, 0f, 0f),
                type = RoadBlockType.POLICE_CHECKPOINT,
                name = "Harare North Police",
                fine = 100f
            ),
            RoadBlock(
                position = Vector3(-200f, 0f, 0f),
                type = RoadBlockType.ZINARA_TOLL_GATE,
                name = "A5 Toll Gate",
                fine = 2f
            ),
            RoadBlock(
                position = Vector3(50f, 100f, 0f),
                type = RoadBlockType.POLICE_CHECKPOINT,
                name = "Mutare Checkpoint",
                fine = 150f
            ),
            RoadBlock(
                position = Vector3(-50f, -150f, 0f),
                type = RoadBlockType.WILDLIFE_GATE,
                name = "Hwange National Park Gate",
                fine = 80f
            )
        ))
    }
    
    fun getNearestRoadBlock(position: Vector3, range: Float = 100f): RoadBlock? {
        return roadBlocks.minByOrNull {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz
        }?.takeIf {
            val dx = it.position.x - position.x
            val dz = it.position.z - position.z
            dx * dx + dz * dz <= range * range
        }
    }
    
    fun getRoadBlocks() = roadBlocks
}
