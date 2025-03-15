package com.example.ecotrack.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecotrack.data.DatabaseHelper
import com.example.ecotrack.data.model.Consumo
@Composable
fun RegistroConsumoScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    var energia by remember { mutableStateOf("") }
    var agua by remember { mutableStateOf("") }
    var transporte by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF1F8E9)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.Home, contentDescription = "Voltar para Home")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Registrar Consumo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = energia,
            onValueChange = { energia = it },
            label = { Text("Consumo de Energia (kWh)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = agua,
            onValueChange = { agua = it },
            label = { Text("Consumo de Água (Litros)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = transporte,
            onValueChange = { transporte = it },
            label = { Text("Distância Percorrida de Carro (km)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val consumo = Consumo(energia = energia, agua = agua, transporte = transporte)
                val success = dbHelper.insertData(consumo)
                if (success) {
                    Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erro ao salvar os dados!", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Consumo", fontSize = 18.sp)
        }
    }
}
