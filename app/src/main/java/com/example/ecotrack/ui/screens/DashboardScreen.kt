package com.example.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DashboardScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF1F8E9)), // Fundo verde claro suave
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard de Consumo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        ConsumptionCard(title = "Consumo de Energia", value = "120 kWh")
        ConsumptionCard(title = "Consumo de Água", value = "350 Litros")
        ConsumptionCard(title = "Emissão de Carbono", value = "15 kg CO₂")
    }
}

@Composable
fun ConsumptionCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 16.sp, color = Color(0xFF004D40))
        }
    }
}
