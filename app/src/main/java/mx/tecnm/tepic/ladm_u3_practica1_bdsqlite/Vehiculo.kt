package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Vehiculo(p:Context) {
    var id = 0
    var placa = ""
    var marca = ""
    var modelo = ""
    var anio = 0
    val pnt = p
    var idConductor = 0

    fun insertar() : Boolean{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).writableDatabase

        val datos = ContentValues()

        datos.put("PLACA",placa)
        datos.put("MARCA",marca)
        datos.put("MODELO",modelo)
        datos.put("ANIO",anio)
        datos.put("IDCONDUCTOR",idConductor)

        val resultado = tablaConductor.insert("VEHICULO",null,datos)
        if(resultado == -1L){
            return false
        }
        return true
    }//insertar

    fun consultar() : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)
                resultado.add(dato)
            }while (cursor.moveToNext())
        }else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//consultar

    fun eliminar(idEliminar:Int):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).writableDatabase

        val resultado = tablaVehiculo.delete("VEHICULO","IDVEHICULO=?", arrayOf(idEliminar.toString()))

        if (resultado == 0) return false
        return true
    }//eliminar

    fun actualizar(idActualizar:String):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val datos = ContentValues()

        datos.put("PLACA",placa)
        datos.put("MARCA",marca)
        datos.put("MODELO",marca)
        datos.put("ANIO",anio)
        datos.put("IDCONDUCTOR",idConductor)

        val resultado = tablaVehiculo.update("VEHICULO",datos,"IDVEHICULO=?", arrayOf(idActualizar))
        if (resultado == 0) return false
        return true
    }//actualizar

    fun obtenerIDs() : ArrayList<Int>{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<Int>()

        val cursor = tablaConductor.query("VEHICULO", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }
        return resultado
    }//obtenerIDs

    fun consultar(idBuscar:String) : Vehiculo{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"IDVEHICULO=?", arrayOf(idBuscar),null,null,null)
        val vehiculo = Vehiculo(MainActivity3())

        if (cursor.moveToFirst()){
            vehiculo.placa = cursor.getString(1)
            vehiculo.marca = cursor.getString(2)
            vehiculo.modelo = cursor.getString(3)
            vehiculo.anio = cursor.getInt(4)
            vehiculo.idConductor = cursor.getInt(5)
        }
        return vehiculo
    }//consultar(idBuscar:String)

    fun consultarVehiculosConductorAsig(ID:String) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"IDCONDUCTOR = ?",arrayOf(ID),null,null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//consultarVehiculosConductorAsig

    fun consultarVehiculosAniosUsados(Anios:Int) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val d = Date()
        val fecha = SimpleDateFormat("yyyy")

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"ANIO = (${fecha.format(d)}-${Anios})",null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//consultarVehiculosAniosUsados

    fun consultarVehiculo() : ArrayList<Vehiculo>{
        val tablaVehiculo = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<Vehiculo>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),null, null,null,null,null)


        if (cursor.moveToFirst()){
            do {
                var ex = Vehiculo(MainActivity5())
                ex.idConductor = cursor.getInt(0)
                ex.placa = cursor.getString(1)
                ex.marca = cursor.getString(2)
                ex.modelo = cursor.getString(3)
                ex.anio = cursor.getInt(4)
                ex.idConductor = cursor.getInt(5)
                resultado.add(ex)
            }while (cursor.moveToNext())
        }
        return resultado
    }//consultarVehiculo

}//class Vehiculo