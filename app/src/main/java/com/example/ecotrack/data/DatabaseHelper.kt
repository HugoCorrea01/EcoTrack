package com.example.ecotrack.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ecotrack.data.model.Consumo

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "consumo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, energia TEXT, agua TEXT, transporte TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS consumo")
        onCreate(db)
    }

    // ✅ Método para inserir dados usando `Consumo`
    fun insertData(consumo: Consumo): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("energia", consumo.energia)
            put("agua", consumo.agua)
            put("transporte", consumo.transporte)
        }
        val result = db.insert("consumo", null, contentValues)
        return result != -1L
    }

    // ✅ Método para recuperar todos os registros de consumo
    fun getAllConsumo(): List<Consumo> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM consumo ORDER BY id ASC", null)

        val consumoList = mutableListOf<Consumo>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val energia = cursor.getString(1)
            val agua = cursor.getString(2)
            val transporte = cursor.getString(3)

            consumoList.add(Consumo(id, energia, agua, transporte))
        }
        cursor.close()
        return consumoList
    }
}
