package com.bussimulatorzim.game.data

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.bussimulatorzim.game.economy.PlayerProfile
import com.bussimulatorzim.game.vehicles.BusModel
import java.io.File

data class GameSaveData(
    val saveId: String,
    val playerName: String,
    val savedAt: Long,
    val gameTime: Float,
    val playerProfile: PlayerProfile,
    val ownedBuses: List<BusModel>,
    val statistics: GameStatistics
)

data class GameStatistics(
    val totalDistance: Float = 0f,
    val totalPassengers: Int = 0,
    val totalTrips: Int = 0,
    val totalEarnings: Float = 0f,
    val totalExpenses: Float = 0f,
    val accidentsCount: Int = 0,
    val ticketsReceived: Int = 0,
    val timePlayedHours: Float = 0f
)

class SaveSystem(context: Context) {
    private val context = context
    private val gson = Gson()
    private val savesDir = File(context.filesDir, "saves").apply { mkdirs() }
    private val statsDir = File(context.filesDir, "stats").apply { mkdirs() }
    
    fun saveGame(gameData: GameSaveData): Boolean {
        return try {
            val saveFile = File(savesDir, "${gameData.saveId}.json")
            val json = gson.toJson(gameData)
            saveFile.writeText(json)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun loadGame(saveId: String): GameSaveData? {
        return try {
            val saveFile = File(savesDir, "$saveId.json")
            if (saveFile.exists()) {
                val json = saveFile.readText()
                gson.fromJson(json, GameSaveData::class.java)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun getAllSaves(): List<GameSaveData> {
        return savesDir.listFiles()?.mapNotNull { file ->
            try {
                val json = file.readText()
                gson.fromJson(json, GameSaveData::class.java)
            } catch (e: Exception) {
                null
            }
        } ?: emptyList()
    }
    
    fun deleteSave(saveId: String): Boolean {
        return try {
            val saveFile = File(savesDir, "$saveId.json")
            saveFile.delete()
        } catch (e: Exception) {
            false
        }
    }
    
    fun saveStatistics(statistics: GameStatistics): Boolean {
        return try {
            val statsFile = File(statsDir, "latest_stats.json")
            val json = gson.toJson(statistics)
            statsFile.writeText(json)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun loadStatistics(): GameStatistics? {
        return try {
            val statsFile = File(statsDir, "latest_stats.json")
            if (statsFile.exists()) {
                val json = statsFile.readText()
                gson.fromJson(json, GameStatistics::class.java)
            } else null
        } catch (e: Exception) {
            null
        }
    }
    
    fun exportSaveData(saveId: String, destinationPath: String): Boolean {
        return try {
            val saveFile = File(savesDir, "$saveId.json")
            val destination = File(destinationPath, "$saveId.json")
            saveFile.copyTo(destination, overwrite = true)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun importSaveData(sourceFile: File): Boolean {
        return try {
            val destFile = File(savesDir, sourceFile.name)
            sourceFile.copyTo(destFile, overwrite = true)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun getSaveSize(saveId: String): Long {
        return File(savesDir, "$saveId.json").length()
    }
    
    fun getTotalSaveDataSize(): Long {
        return savesDir.listFiles()?.sumOf { it.length() } ?: 0L
    }
}
