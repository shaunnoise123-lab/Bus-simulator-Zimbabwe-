package com.bussimulatorzim.game.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val usdBalance: Float,
    val zigBalance: Float,
    val reputation: Float,
    val level: Int,
    val totalMileage: Float,
    val totalPassengers: Int,
    val totalTrips: Int,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "buses")
data class BusEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val busType: String,
    val licensePlate: String,
    val purchaseDate: Long,
    val currentMileage: Float,
    val engineHealth: Float,
    val bodyHealth: Float,
    val windshieldHealth: Float,
    val fuelLevel: Float,
    val lastServiceDate: Long,
    val totalEarnings: Float,
    val totalServiceCost: Float,
    val totalFuelCost: Float,
    val totalRepairCost: Float
)

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val busId: String,
    val startCity: String,
    val endCity: String,
    val startTime: Long,
    val endTime: Long,
    val distance: Float,
    val passengers: Int,
    val revenue: Float,
    val fuelUsed: Float,
    val damageOccurred: Boolean,
    val damageAmount: Float,
    val finesReceived: Float
)

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val violationType: String,
    val fineAmount: Float,
    val issuedAt: Long,
    val isPaid: Boolean,
    val paidAt: Long? = null
)

@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val name: String,
    val reputation: Float,
    val totalEarnings: Float,
    val totalExpenses: Float,
    val busCount: Int,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val achievementId: String,
    val title: String,
    val unlockedAt: Long,
    val reward: Float
)
