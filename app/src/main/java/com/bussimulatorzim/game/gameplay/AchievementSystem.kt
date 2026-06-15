package com.bussimulatorzim.game.gameplay

data class AchievementType(
    val id: String,
    val title: String,
    val description: String,
    val reward: Float,
    val icon: String
)

data class Achievement(
    val type: AchievementType,
    val unlockedAt: Long = System.currentTimeMillis(),
    var isNotified: Boolean = false
)

class AchievementSystem {
    private val unlockedAchievements = mutableListOf<Achievement>()
    private val achievements = listOf(
        AchievementType("first_trip", "First Trip", "Complete your first route", 100f, "ic_trophy"),
        AchievementType("safe_driver", "Safe Driver", "Drive 100 km without accidents", 500f, "ic_shield"),
        AchievementType("millionaire", "Millionaire", "Earn $1,000,000", 0f, "ic_money"),
        AchievementType("company_owner", "Company Owner", "Own your own company", 1000f, "ic_briefcase"),
        AchievementType("speed_demon", "Speed Demon", "Reach 180 km/h", 200f, "ic_speed"),
        AchievementType("outlaw", "Outlaw", "Evade police 10 times", 300f, "ic_wanted"),
        AchievementType("perfect_reputation", "Perfect Reputation", "Reach 100% reputation", 2000f, "ic_star"),
        AchievementType("all_routes", "World Traveler", "Complete all routes", 5000f, "ic_map")
    )
    
    fun unlockAchievement(achievementId: String) {
        val achievement = achievements.find { it.id == achievementId }
        if (achievement != null && !isUnlocked(achievementId)) {
            unlockedAchievements.add(Achievement(achievement))
        }
    }
    
    fun isUnlocked(achievementId: String): Boolean {
        return unlockedAchievements.any { it.type.id == achievementId }
    }
    
    fun getUnlockedAchievements() = unlockedAchievements
    fun getAllAchievements() = achievements
}
