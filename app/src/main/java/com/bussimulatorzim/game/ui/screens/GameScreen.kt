package com.bussimulatorzim.game.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussimulatorzim.game.ui.CockpitDisplay
import com.bussimulatorzim.game.ui.GameHUD

@Composable
fun GameScreen() {
    // Game state
    var speed by remember { mutableStateOf(0f) }
    var rpm by remember { mutableStateOf(0f) }
    var fuel by remember { mutableStateOf(100f) }
    var money by remember { mutableStateOf(5000f) }
    var passengers by remember { mutableStateOf(0) }
    var timeDisplay by remember { mutableStateOf("06:00") }
    var repairHealth by remember { mutableStateOf(80f) }
    var gear by remember { mutableStateOf("N") }
    var engineTemp by remember { mutableStateOf(70f) }
    var oilPressure by remember { mutableStateOf(100f) }
    var batteryVoltage by remember { mutableStateOf(14f) }
    var engineRunning by remember { mutableStateOf(false) }
    var headlights by remember { mutableStateOf(false) }
    var wipers by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Game view (3D rendered area)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "3D Game View - Zimbabwe Roads",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 24.sp
                )
            )
        }
        
        // HUD Overlay
        GameHUD(
            speed = speed,
            rpm = rpm,
            fuel = fuel,
            money = money,
            passengers = passengers,
            timeDisplay = timeDisplay,
            repairHealth = repairHealth,
            gear = gear
        )
        
        // Cockpit Display at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
        ) {
            CockpitDisplay(
                engineTemp = engineTemp,
                oilPressure = oilPressure,
                batteryVoltage = batteryVoltage,
                engineRunning = engineRunning,
                headlights = headlights,
                wipers = wipers
            )
        }
        
        // Controls at corners
        ControlPanel(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            onSteerLeft = { /* Handle steering */ },
            onSteerRight = { /* Handle steering */ },
            onAccelerate = { /* Handle acceleration */ },
            onBrake = { /* Handle braking */ }
        )
    }
}

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    onSteerLeft: () -> Unit,
    onSteerRight: () -> Unit,
    onAccelerate: () -> Unit,
    onBrake: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0x80000000),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = "Controls Ready",
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp
            )
        )
    }
}
