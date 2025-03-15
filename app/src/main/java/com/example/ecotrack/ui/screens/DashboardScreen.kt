package com.example.ecotrack.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.ecotrack.data.api.ApiClient
import com.example.ecotrack.data.api.WeatherService
import com.example.ecotrack.data.DatabaseHelper
import com.example.ecotrack.data.model.WeatherResponse
import com.example.ecotrack.data.model.Consumo
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    var consumoList by remember { mutableStateOf(emptyList<Consumo>()) }
    var weatherInfo by remember { mutableStateOf("Carregando clima...") }
    var locationError by remember { mutableStateOf(false) }

    // 📌 Buscar os dados do SQLite
    LaunchedEffect(Unit) {
        consumoList = dbHelper.getAllConsumo()
    }

    // 📌 Requisição para a API de Clima
    LaunchedEffect(Unit) {
        val weatherService = ApiClient.retrofit.create(WeatherService::class.java)
        val call = weatherService.getWeatherByLocation(
            latitude = -23.5505, // 📌 São Paulo (exemplo)
            longitude = -46.6333,
            apiKey = "cae2f1a0f63a452982acda961653d9ea"
        )

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    weatherInfo = "🌡 ${weather?.main?.temp}°C - ${weather?.weather?.firstOrNull()?.description ?: "Sem dados"}"
                } else {
                    locationError = true
                    weatherInfo = "⚠ Erro ao obter clima"
                    Log.e("API_ERROR", "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                locationError = true
                weatherInfo = "⚠ Erro de conexão"
                Log.e("API_ERROR", "Falha na conexão: ${t.message}")
            }
        })
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFB2DFDB), Color(0xFF00796B))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 📌 Cartão Clima
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🌍 Clima Atual", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(weatherInfo, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📊 Exibir o Gráfico de Consumo
            ConsumptionChart(consumoList)

            Spacer(modifier = Modifier.height(16.dp))

            // 📋 Exibir os Dados de Consumo
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(consumoList) { consumo ->
                    ConsumoItem(consumo)
                }
            }
        }
    }
}

// 📌 Componente para exibir cada registro de consumo
@Composable
fun ConsumoItem(consumo: Consumo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("⚡ Energia: ${consumo.energia} kWh", style = MaterialTheme.typography.bodyLarge)
            Text("💧 Água: ${consumo.agua} L", style = MaterialTheme.typography.bodyLarge)
            Text("🚗 Transporte: ${consumo.transporte} km", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// 📊 Função para exibir o gráfico de consumo

@Composable
fun ConsumptionChart(consumoList: List<Consumo>) {
    if (consumoList.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp) // Aumentamos a altura para garantir espaçamento adequado
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📊 Consumo de Energia",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                AndroidView(
                    factory = { context ->
                        BarChart(context).apply {
                            description.isEnabled = false
                            setDrawGridBackground(false)
                            setTouchEnabled(true)
                            isDragEnabled = true
                            setScaleEnabled(true)
                            setFitBars(true)
                            animateY(1000)

                            xAxis.apply {
                                position = XAxis.XAxisPosition.BOTTOM
                                setDrawGridLines(false)
                                granularity = 1f
                                textSize = 14f
                                labelRotationAngle = -30f // Inclina os rótulos para melhor leitura
                            }

                            axisLeft.apply {
                                setDrawGridLines(true)
                                axisMinimum = 0f
                                textSize = 14f
                            }

                            axisRight.isEnabled = false
                            legend.textSize = 14f
                        }
                    },
                    update = { chart ->
                        val entries = consumoList.mapIndexed { index, consumo ->
                            BarEntry(index.toFloat(), consumo.energia.toFloatOrNull() ?: 0f)
                        }

                        if (entries.isNotEmpty()) {
                            val dataSet = BarDataSet(entries, "Consumo de Energia (kWh)").apply {
                                colors = ColorTemplate.MATERIAL_COLORS.toList()
                                valueTextSize = 14f
                            }

                            val barData = BarData(dataSet).apply {
                                barWidth = 0.8f // Ajusta o tamanho das barras
                            }

                            chart.data = barData
                            chart.setFitBars(true)
                            chart.invalidate()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) // Garante um tamanho adequado para o gráfico
                )
            }
        }
    } else {
        Text(
            "📌 Nenhum dado disponível para exibir no gráfico.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}



