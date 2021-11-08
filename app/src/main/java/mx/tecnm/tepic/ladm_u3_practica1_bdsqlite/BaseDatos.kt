package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//MODELO
class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p: SQLiteDatabase) {
        p.execSQL("CREATE TABLE CONDUCTOR(IDCONDUCTOR INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NOMBRE VARCHAR(200), DOMICILIO VARCHAR(200), NOLICENCIA VARCHAR(200), VENCE DATE)")
        p.execSQL("CREATE TABLE VEHICULO(IDVEHICULO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, PLACA VARCHAR(200), MARCA VARCHAR(200), MODELO VARCHAR(200), ANIO INTEGER, IDCONDUCTOR INTEGER REFERENCES CONDUCTOR(IDCONDUCTOR))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}