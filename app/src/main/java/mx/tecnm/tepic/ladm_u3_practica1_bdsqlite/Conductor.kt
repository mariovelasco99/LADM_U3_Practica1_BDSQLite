package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//CONTROLADOR
class Conductor(p:Context) {
    var nombre = ""
    var domicilio = ""
    var noLicencia = ""
    var vence = ""
    val pnt = p
    var id = 0

    fun insertar() : Boolean{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).writableDatabase
        val datos = ContentValues()

        //Asiganacion para guardar
        datos.put("NOMBRE",nombre)
        datos.put("DOMICILIO",domicilio)
        datos.put("NOLICENCIA",noLicencia)
        datos.put("VENCE",vence)

        val resultado = tablaConductor.insert("CONDUCTOR",null,datos)

        if (resultado == -1L){
            return false
        }
        return true
    }//insertar

    fun consultar() :ArrayList<String>{
        val tablaConductor = BaseDatos(pnt, "VANS", null,1).readableDatabase
        val resultado = ArrayList<String>()
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do{
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                                "\nNo Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)
                resultado.add(dato)
            }while (cursor.moveToNext())
        }else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//consultar

    fun obtenerIDs() : ArrayList<Int>{
        val tablaConductor = BaseDatos(pnt, "VANS",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if(cursor.moveToFirst()){
            do{
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }//if
        return resultado
    }//obtenerIDs

    fun consultar(idBuscar:String) : Conductor{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR=?", arrayOf(idBuscar),null,null,null)
        val conductor = Conductor(MainActivity2())

        if (cursor.moveToFirst()){
            conductor.nombre = cursor.getString(1)
            conductor.domicilio = cursor.getString(2)
            conductor.noLicencia = cursor.getString(3)
            conductor.vence = cursor.getString(4)
        }//if
        return conductor
    }//consultar(idBuscar:String) : Conductor

    fun eliminar(idEliminar:Int):Boolean{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).writableDatabase
        val resultado = tablaConductor.delete("CONDUCTOR","IDCONDUCTOR=?", arrayOf(idEliminar.toString()))

        if (resultado == 0) return false
        return true
    }//eliminar

    fun actualizar(idActualizar:String):Boolean{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val datos = ContentValues()

        datos.put("NOMBRE",nombre)
        datos.put("DOMICILIO",domicilio)
        datos.put("NOLICENCIA",noLicencia)
        datos.put("VENCE",vence)

        val resultado = tablaConductor.update("CONDUCTOR",datos,"IDCONDUCTOR=?", arrayOf(idActualizar))
        if (resultado == 0) return false
        return true
    }//actualizar

    fun licenciaVencida() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<String>()
        val d = Date()
        val fecha = SimpleDateFormat("yyyy-MM-dd")

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"VENCE<?", arrayOf(fecha.format(d)),null,null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//licenciaVencida

    fun sinAsignarVehiculo() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()
        var ids = "("

        val consulta = tablaConductor.query("VEHICULO", arrayOf("IDCONDUCTOR"),null,null,null,null,null)

        if (consulta.moveToFirst()){
            do {
                ids += "'"+consulta.getInt(0)+"'"
                if (consulta.moveToNext()){
                    ids += ","
                    consulta.moveToPrevious()
                } else {
                    ids += ")"
                }
            }while (consulta.moveToNext())
            System.out.println(ids)
        }

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR NOT IN ${ids}", null,null, null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA PARA MOSTRAR")
        }
        return resultado
    }//sinAsignarVehiculo

    fun consultarConductVehiculo(ID:String) : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<String>()
        var id = 0

        val consulta = tablaConductor.query("VEHICULO", arrayOf("IDCONDUCTOR"),"IDVEHICULO = ?",
            arrayOf(ID),null,null,null)

        if (consulta.moveToFirst()){
            id = consulta.getInt(0)
        }

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR = ?",
            arrayOf(id.toString()),null, null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }//consultarConductVehiculo

    fun consultarConduct() : ArrayList<Conductor>{
        val tablaConductor = BaseDatos(pnt,"VANS",null,1).readableDatabase
        val resultado = ArrayList<Conductor>()
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                var c = Conductor(MainActivity5())
                c.id = cursor.getInt(0)
                c.nombre = cursor.getString(1)
                c.domicilio = cursor.getString(2)
                c.noLicencia = cursor.getString(3)
                c.vence = cursor.getString(4)

                resultado.add(c)
            }while (cursor.moveToNext())
        }
        return resultado
    }//consultarConduct

}//classConductor