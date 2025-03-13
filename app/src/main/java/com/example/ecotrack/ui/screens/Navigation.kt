package com.example.ecotrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun EcoTrackApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("registro") { RegistroConsumoScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("desafios") { DesafiosScreen(navController) }
        composable("ranking") { RankingScreen(navController) }
        composable("menu") { MenuScreen(navController) }
    }
}

@Composable
fun DesafiosScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desafios Sustentáveis", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Aqui serão exibidos os desafios disponíveis.")
    }
}

@Composable
fun RankingScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ranking de Usuários", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Aqui será exibido o ranking dos usuários com mais EcoPontos.")
    }
}

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Menu Principal", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text("Início")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("registro") }) {
            Text("Registrar Consumo")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Ver Estatísticas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("desafios") }) {
            Text("Desafios Sustentáveis")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("ranking") }) {
            Text("Ranking")
        }
    }
}
