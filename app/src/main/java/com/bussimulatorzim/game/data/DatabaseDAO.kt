package com.bussimulatorzim.game.data

import androidx.room.*

@Dao
interface PlayerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)
    
    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayer(playerId: String): PlayerEntity?
    
    @Query("SELECT * FROM players ORDER BY reputation DESC LIMIT :limit")
    suspend fun getTopPlayers(limit: Int): List<PlayerEntity>
    
    @Update
    suspend fun updatePlayer(player: PlayerEntity)
    
    @Delete
    suspend fun deletePlayer(player: PlayerEntity)
}

@Dao
interface BusDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBus(bus: BusEntity)
    
    @Query("SELECT * FROM buses WHERE playerId = :playerId")
    suspend fun getPlayerBuses(playerId: String): List<BusEntity>
    
    @Query("SELECT * FROM buses WHERE id = :busId")
    suspend fun getBus(busId: String): BusEntity?
    
    @Update
    suspend fun updateBus(bus: BusEntity)
    
    @Delete
    suspend fun deleteBus(bus: BusEntity)
}

@Dao
interface TripDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)
    
    @Query("SELECT * FROM trips WHERE playerId = :playerId ORDER BY startTime DESC LIMIT :limit")
    suspend fun getPlayerTrips(playerId: String, limit: Int): List<TripEntity>
    
    @Query("SELECT SUM(revenue) FROM trips WHERE playerId = :playerId")
    suspend fun getTotalRevenue(playerId: String): Float?
    
    @Query("SELECT COUNT(*) FROM trips WHERE playerId = :playerId")
    suspend fun getTripCount(playerId: String): Int
    
    @Update
    suspend fun updateTrip(trip: TripEntity)
}

@Dao
interface TicketDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity)
    
    @Query("SELECT * FROM tickets WHERE playerId = :playerId ORDER BY issuedAt DESC")
    suspend fun getPlayerTickets(playerId: String): List<TicketEntity>
    
    @Query("SELECT SUM(fineAmount) FROM tickets WHERE playerId = :playerId AND isPaid = 0")
    suspend fun getUnpaidFinesAmount(playerId: String): Float?
    
    @Update
    suspend fun updateTicket(ticket: TicketEntity)
}

@Dao
interface CompanyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyEntity)
    
    @Query("SELECT * FROM companies WHERE id = :companyId")
    suspend fun getCompany(companyId: String): CompanyEntity?
    
    @Query("SELECT * FROM companies WHERE playerId = :playerId")
    suspend fun getPlayerCompany(playerId: String): CompanyEntity?
    
    @Update
    suspend fun updateCompany(company: CompanyEntity)
}

@Dao
interface AchievementDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)
    
    @Query("SELECT * FROM achievements WHERE playerId = :playerId")
    suspend fun getPlayerAchievements(playerId: String): List<AchievementEntity>
    
    @Query("SELECT COUNT(*) FROM achievements WHERE playerId = :playerId")
    suspend fun getAchievementCount(playerId: String): Int
}
