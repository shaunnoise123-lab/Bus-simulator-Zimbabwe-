package com.bussimulatorzim.game.gameplay

enum class ArrestReason {
    ROADBLOCK_EVASION,
    ILLEGAL_GOODS_POSSESSION,
    RECKLESS_DRIVING,
    UNPAID_FINES,
    HIT_AND_RUN,
    SPEEDING_VIOLATION
}

data class ArrestRecord(
    val reason: ArrestReason,
    val arrestTime: Long = System.currentTimeMillis(),
    val fineAmount: Float = 0f
)

class JailSystem {
    private var isPlayerJailed = false
    private var jailTimeRemaining = 0f
    private val jailDuration = 10800f
    private val arrestRecords = mutableListOf<ArrestRecord>()
    
    fun arrestPlayer(reason: ArrestReason, fineAmount: Float) {
        isPlayerJailed = true
        jailTimeRemaining = jailDuration
        arrestRecords.add(ArrestRecord(reason, fineAmount = fineAmount))
    }
    
    fun bailOut(bailAmount: Float): Boolean {
        if (bailAmount > 0) {
            isPlayerJailed = false
            jailTimeRemaining = 0f
            return true
        }
        return false
    }
    
    fun update(deltaTime: Float) {
        if (isPlayerJailed && jailTimeRemaining > 0) {
            jailTimeRemaining -= deltaTime
            if (jailTimeRemaining <= 0) {
                releaseFromJail()
            }
        }
    }
    
    private fun releaseFromJail() {
        isPlayerJailed = false
        jailTimeRemaining = 0f
    }
    
    fun isJailed() = isPlayerJailed
    fun getJailTimeRemaining() = jailTimeRemaining
    fun getJailTimeFormatted(): String {
        val hours = (jailTimeRemaining / 3600).toInt()
        val minutes = ((jailTimeRemaining % 3600) / 60).toInt()
        return String.format("%dh %dm", hours, minutes)
    }
    
    fun getArrestRecords() = arrestRecords
}
