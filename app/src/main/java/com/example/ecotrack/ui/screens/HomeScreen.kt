package com.example.ecotrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo ao EcoTrack", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("registro") }) {
            Text("Registrar Consumo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Ver Estatísticas")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("desafios") }) {
            Text("Participar de Desafios")
        }
    }
}
