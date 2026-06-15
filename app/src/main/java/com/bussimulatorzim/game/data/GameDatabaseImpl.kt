package com.bussimulatorzim.game.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PlayerEntity::class,
        BusEntity::class,
        TripEntity::class,
        TicketEntity::class,
        CompanyEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GameDatabaseImpl : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
    abstract fun busDAO(): BusDAO
    abstract fun tripDAO(): TripDAO
    abstract fun ticketDAO(): TicketDAO
    abstract fun companyDAO(): CompanyDAO
    abstract fun achievementDAO(): AchievementDAO
    
    companion object {
        private var instance: GameDatabaseImpl? = null
        
        fun getInstance(context: android.content.Context): GameDatabaseImpl {
            if (instance == null) {
                instance = androidx.room.Room.databaseBuilder(
                    context,
                    GameDatabaseImpl::class.java,
                    "bus_simulator_database"
                ).build()
            }
            return instance!!
        }
    }
}
