package com.example.ecotrack.ui.screens

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class DashboardDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "consumo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, energia TEXT, agua TEXT, transporte TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS consumo")
        onCreate(db)
    }

    fun getLastEntries(): List<Triple<Float, Float, Float>> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT energia, agua, transporte FROM consumo ORDER BY id ASC LIMIT 5", null)
        val dataList = mutableListOf<Triple<Float, Float, Float>>()

        while (cursor.moveToNext()) {
            val energia = cursor.getString(0)?.toFloatOrNull() ?: 0f
            val agua = cursor.getString(1)?.toFloatOrNull() ?: 0f
            val transporte = cursor.getString(2)?.toFloatOrNull() ?: 0f
            dataList.add(Triple(energia, agua, transporte))
        }
        cursor.close()
        return dataList
    }
}

@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = remember { DashboardDatabaseHelper(context) }
    var consumoData by remember { mutableStateOf(emptyList<Triple<Float, Float, Float>>()) }

    LaunchedEffect(Unit) {
        consumoData = dbHelper.getLastEntries()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(androidx.compose.ui.graphics.Color(0xFFF1F8E9))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard de Consumo",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (consumoData.isNotEmpty()) {
            ConsumptionChart(consumoData)
            Spacer(modifier = Modifier.height(16.dp))
            ConsumptionList(consumoData)
        } else {
            Text("Nenhum dado disponível.", fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Gray)
        }
    }
}

@Composable
fun ConsumptionChart(consumoData: List<Triple<Float, Float, Float>>) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        factory = { ctx ->
            BarChart(ctx).apply {
                description.isEnabled = false
                setDrawGridBackground(false)

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.granularity = 1f
                xAxis.labelCount = consumoData.size

                axisLeft.setDrawGridLines(false)
                axisRight.isEnabled = false

                val entries = consumoData.mapIndexed { index, data ->
                    BarEntry(index.toFloat(), data.first)
                }

                val dataSet = BarDataSet(entries, "Consumo de Energia (kWh)").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                    valueTextSize = 12f
                }

                data = BarData(dataSet)
                invalidate()
            }
        }
    )
}

@Composable
fun ConsumptionList(consumoData: List<Triple<Float, Float, Float>>) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        consumoData.forEachIndexed { index, data ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Registro ${index + 1}", fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Energia: ${data.first} kWh")
                    Text("Água: ${data.second} L")
                    Text("Transporte: ${data.third} km")
                }
            }
        }
    }
}
