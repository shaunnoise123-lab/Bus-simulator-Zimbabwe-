package com.bussimulatorzim.game.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val database: GameDatabaseImpl) {
    
    // Player Repository
    suspend fun savePlayer(player: PlayerEntity) = withContext(Dispatchers.IO) {
        database.playerDAO().insertPlayer(player)
    }
    
    suspend fun getPlayer(playerId: String) = withContext(Dispatchers.IO) {
        database.playerDAO().getPlayer(playerId)
    }
    
    suspend fun updatePlayer(player: PlayerEntity) = withContext(Dispatchers.IO) {
        database.playerDAO().updatePlayer(player)
    }
    
    suspend fun getTopPlayers(limit: Int = 100) = withContext(Dispatchers.IO) {
        database.playerDAO().getTopPlayers(limit)
    }
    
    // Bus Repository
    suspend fun saveBus(bus: BusEntity) = withContext(Dispatchers.IO) {
        database.busDAO().insertBus(bus)
    }
    
    suspend fun getPlayerBuses(playerId: String) = withContext(Dispatchers.IO) {
        database.busDAO().getPlayerBuses(playerId)
    }
    
    suspend fun getBus(busId: String) = withContext(Dispatchers.IO) {
        database.busDAO().getBus(busId)
    }
    
    suspend fun updateBus(bus: BusEntity) = withContext(Dispatchers.IO) {
        database.busDAO().updateBus(bus)
    }
    
    // Trip Repository
    suspend fun saveTrip(trip: TripEntity) = withContext(Dispatchers.IO) {
        database.tripDAO().insertTrip(trip)
    }
    
    suspend fun getPlayerTrips(playerId: String, limit: Int = 50) = withContext(Dispatchers.IO) {
        database.tripDAO().getPlayerTrips(playerId, limit)
    }
    
    suspend fun getTotalRevenue(playerId: String) = withContext(Dispatchers.IO) {
        database.tripDAO().getTotalRevenue(playerId) ?: 0f
    }
    
    suspend fun getTripCount(playerId: String) = withContext(Dispatchers.IO) {
        database.tripDAO().getTripCount(playerId)
    }
    
    // Ticket Repository
    suspend fun saveTicket(ticket: TicketEntity) = withContext(Dispatchers.IO) {
        database.ticketDAO().insertTicket(ticket)
    }
    
    suspend fun getPlayerTickets(playerId: String) = withContext(Dispatchers.IO) {
        database.ticketDAO().getPlayerTickets(playerId)
    }
    
    suspend fun getUnpaidFinesAmount(playerId: String) = withContext(Dispatchers.IO) {
        database.ticketDAO().getUnpaidFinesAmount(playerId) ?: 0f
    }
    
    // Company Repository
    suspend fun saveCompany(company: CompanyEntity) = withContext(Dispatchers.IO) {
        database.companyDAO().insertCompany(company)
    }
    
    suspend fun getCompany(companyId: String) = withContext(Dispatchers.IO) {
        database.companyDAO().getCompany(companyId)
    }
    
    suspend fun getPlayerCompany(playerId: String) = withContext(Dispatchers.IO) {
        database.companyDAO().getPlayerCompany(playerId)
    }
    
    suspend fun updateCompany(company: CompanyEntity) = withContext(Dispatchers.IO) {
        database.companyDAO().updateCompany(company)
    }
    
    // Achievement Repository
    suspend fun saveAchievement(achievement: AchievementEntity) = withContext(Dispatchers.IO) {
        database.achievementDAO().insertAchievement(achievement)
    }
    
    suspend fun getPlayerAchievements(playerId: String) = withContext(Dispatchers.IO) {
        database.achievementDAO().getPlayerAchievements(playerId)
    }
    
    suspend fun getAchievementCount(playerId: String) = withContext(Dispatchers.IO) {
        database.achievementDAO().getAchievementCount(playerId)
    }
}
