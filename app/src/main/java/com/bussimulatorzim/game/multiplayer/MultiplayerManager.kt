package com.bussimulatorzim.game.multiplayer

import android.content.Context
import com.bussimulatorzim.game.economy.PlayerProfile

data class PlayerLeaderboardEntry(
    val rank: Int,
    val playerName: String,
    val totalEarnings: Float,
    val reputation: Float,
    val level: Int,
    val totalTrips: Int,
    val lastUpdate: Long
)

data class MultiplayerSession(
    val sessionId: String,
    val host: PlayerProfile,
    val players: List<PlayerProfile>,
    val maxPlayers: Int = 4,
    val isPublic: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

class LeaderboardManager {
    private val leaderboards = mutableMapOf<String, MutableList<PlayerLeaderboardEntry>>()
    private var entryCounter = 0
    
    init {
        // Initialize leaderboard categories
        leaderboards["earnings"] = mutableListOf()
        leaderboards["reputation"] = mutableListOf()
        leaderboards["trips"] = mutableListOf()
        leaderboards["level"] = mutableListOf()
    }
    
    fun submitScore(category: String, player: PlayerProfile, value: Float) {
        val entry = PlayerLeaderboardEntry(
            rank = 0,
            playerName = player.playerName,
            totalEarnings = player.usdBalance,
            reputation = player.reputation,
            level = player.level,
            totalTrips = player.totalTrips,
            lastUpdate = System.currentTimeMillis()
        )
        
        val leaderboard = leaderboards[category] ?: mutableListOf()
        leaderboard.add(entry)
        leaderboard.sortByDescending { it.totalEarnings }
        leaderboards[category] = leaderboard.take(100).toMutableList()
    }
    
    fun getLeaderboard(category: String, limit: Int = 50): List<PlayerLeaderboardEntry> {
        return (leaderboards[category] ?: emptyList()).take(limit).mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        }
    }
    
    fun getPlayerRank(category: String, playerName: String): Int? {
        return leaderboards[category]?.indexOfFirst { it.playerName == playerName }?.plus(1)
    }
    
    fun getTopPlayers(count: Int = 10): List<PlayerLeaderboardEntry> {
        return leaderboards["earnings"]?.take(count)?.mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        } ?: emptyList()
    }
}

class MultiplayerManager(context: Context) {
    private val activeSessions = mutableMapOf<String, MultiplayerSession>()
    private val leaderboardManager = LeaderboardManager()
    private var sessionIdCounter = 0
    
    fun createSession(host: PlayerProfile, maxPlayers: Int = 4, isPublic: Boolean = true): MultiplayerSession {
        val session = MultiplayerSession(
            sessionId = "session_${sessionIdCounter++}",
            host = host,
            players = listOf(host),
            maxPlayers = maxPlayers,
            isPublic = isPublic
        )
        activeSessions[session.sessionId] = session
        return session
    }
    
    fun joinSession(sessionId: String, player: PlayerProfile): Boolean {
        val session = activeSessions[sessionId] ?: return false
        if (session.players.size >= session.maxPlayers) return false
        
        val updatedPlayers = session.players + player
        activeSessions[sessionId] = session.copy(players = updatedPlayers)
        return true
    }
    
    fun leaveSession(sessionId: String, player: PlayerProfile): Boolean {
        val session = activeSessions[sessionId] ?: return false
        val updatedPlayers = session.players.filter { it.playerName != player.playerName }
        
        if (updatedPlayers.isEmpty()) {
            activeSessions.remove(sessionId)
        } else {
            activeSessions[sessionId] = session.copy(players = updatedPlayers)
        }
        return true
    }
    
    fun getPublicSessions(): List<MultiplayerSession> {
        return activeSessions.values.filter { it.isPublic }.toList()
    }
    
    fun submitPlayerStats(player: PlayerProfile) {
        leaderboardManager.submitScore("earnings", player, player.usdBalance)
        leaderboardManager.submitScore("reputation", player, player.reputation)
        leaderboardManager.submitScore("level", player, player.level.toFloat())
        leaderboardManager.submitScore("trips", player, player.totalTrips.toFloat())
    }
    
    fun getLeaderboard(category: String, limit: Int = 50) = leaderboardManager.getLeaderboard(category, limit)
    
    fun getPlayerRank(category: String, playerName: String) = leaderboardManager.getPlayerRank(category, playerName)
    
    fun getTopPlayers(count: Int = 10) = leaderboardManager.getTopPlayers(count)
}
