package com.bussimulatorzim.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameHUD(
    speed: Float,
    rpm: Float,
    fuel: Float,
    money: Float,
    passengers: Int,
    timeDisplay: String,
    repairHealth: Float,
    gear: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top HUD - Speed, RPM, Time
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HUDPanel("SPEED", "${speed.toInt()} km/h", Color(0xFF4CAF50))
            HUDPanel("TIME", timeDisplay, Color(0xFF2196F3))
            HUDPanel("RPM", "${rpm.toInt()}", Color(0xFFFF9800))
        }

        // Bottom Left - Money and Passengers
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            HUDPanel("MONEY", "USD ${money.toInt()}", Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(8.dp))
            HUDPanel("PASSENGERS", passengers.toString(), Color(0xFF9C27B0))
        }

        // Bottom Right - Fuel and Health
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            HUDPanel("FUEL", "${fuel.toInt()}%", Color(0xFFFF5722))
            Spacer(modifier = Modifier.height(8.dp))
            HUDPanel("HEALTH", "${repairHealth.toInt()}%", Color(0xFF2196F3))
        }

        // Center - Gear Display
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    color = Color(0x80000000),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = gear,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 48.sp
                )
            )
        }
    }
}

@Composable
fun HUDPanel(label: String, value: String, color: Color) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0x80000000),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = color,
                    fontSize = 10.sp
                )
            )
            Text(
                text = value,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun CockpitDisplay(
    engineTemp: Float,
    oilPressure: Float,
    batteryVoltage: Float,
    engineRunning: Boolean,
    headlights: Boolean,
    wipers: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DashboardGauge("ENGINE TEMP", engineTemp, 120f, Color(0xFFFF5722))
            DashboardGauge("OIL PRESSURE", oilPressure, 150f, Color(0xFF4CAF50))
            DashboardGauge("BATTERY", batteryVoltage, 14f, Color(0xFF2196F3))
            
            // Status indicators
            Column {
                IndicatorLight("ENGINE", engineRunning)
                IndicatorLight("LIGHTS", headlights)
                IndicatorLight("WIPERS", wipers)
            }
        }
    }
}

@Composable
fun DashboardGauge(label: String, value: Float, max: Float, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(label, style = TextStyle(color = Color.White, fontSize = 8.sp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(
                    color = Color(0xFF333333),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${(value / max * 100).toInt()}%",
                style = TextStyle(color = color, fontSize = 10.sp)
            )
        }
    }
}

@Composable
fun IndicatorLight(label: String, isActive: Boolean) {
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (isActive) Color(0xFF4CAF50) else Color(0xFF666666),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = TextStyle(color = Color.White, fontSize = 8.sp))
    }
}
