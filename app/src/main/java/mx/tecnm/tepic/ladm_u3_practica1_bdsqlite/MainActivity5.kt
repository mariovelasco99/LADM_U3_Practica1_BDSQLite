package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main5.*
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        botonExpConductores.setOnClickListener {
            if (guardarConductor()){
                Toast.makeText(this,"CONDUCTORES GUARDADOS EXITOSAMENTE",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this,"ERROR!! AL GUARDAR CONDUCTORES",Toast.LENGTH_LONG).show()
            }
        }//botonExpConductores

        botonExpVehiculos.setOnClickListener {
            if (guardarVehi()){
                Toast.makeText(this,"LOS VEHICULOS SE GUARDARARON CON EXITO",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this,"ERROR!! AL GUARDAR LOS VEHICULOS",Toast.LENGTH_LONG).show()
            }
        }//botonExpVehiculos

        botonExpConducVehi.setOnClickListener {
            if (guardarConducVehi()){
                Toast.makeText(this,"LOS CONDUCTORES Y VEHICULOS SE EXITOSAMENTE",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this,"ERROR!! AL GUARDAR LOS CONDUCTORES Y VEHICULOS",Toast.LENGTH_LONG).show()
            }
        }//botonExpConducVehi

        botonExpVehiConduc.setOnClickListener {
            if (guardarVehiConduc()){
                Toast.makeText(this,"LOS VEHICULOS Y CONDUCTORES SE EXITOSAMENTE",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this,"ERROR!! AL GUARDAR LOS VEHICULOS Y CONDUCTORES",Toast.LENGTH_LONG).show()
            }
        }//botonExpVehiConduc

        botonRegresarMP.setOnClickListener {
            finish()
        }//botonRegresarM
    }//onCreate

    fun guardarConductor() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Conductor(this).consultarConduct()
            val doc = OutputStreamWriter(openFileOutput("docConductores.txt", MODE_PRIVATE))

            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).id.toString()+","+arreglo.get(it).nombre+","+arreglo.get(it).domicilio+","+arreglo.get(it).noLicencia+","+arreglo.get(it).vence
            }
            doc.write(datacontenido)
            doc.flush()
            doc.close()
            return true
        }catch (io: IOException){ return false }
    }//guardarConductor


    fun guardarVehi() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Vehiculo(this).consultarVehiculo()
            val doc = OutputStreamWriter(openFileOutput("docVehiculos.txt", MODE_PRIVATE))

            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).id.toString()+","+arreglo.get(it).placa+","+arreglo.get(it).marca+","+arreglo.get(it).modelo+","+arreglo.get(it).anio+","+arreglo.get(it).idConductor
            }
            doc.write(datacontenido)
            doc.flush()
            doc.close()
            return true
        } catch (io: IOException){ return false }
    }//guardarVehi

    fun guardarConducVehi() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Vehiculo(this).consultarVehiculo()
            val arreglo2 = Conductor(this).consultarConduct()
            val doc = OutputStreamWriter(openFileOutput("docCondVehi.txt", MODE_PRIVATE))

            //PARA EL CONDUCTOR
            (0 until arreglo2.size).forEach {
                datacontenido += arreglo2.get(it).id.toString()+","+arreglo2.get(it).nombre+","+arreglo2.get(it).domicilio+","+arreglo2.get(it).noLicencia+","+arreglo2.get(it).vence
            }

            //PARA EL VEHICULO
            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).id.toString()+","+arreglo.get(it).placa+","+arreglo.get(it).marca+","+arreglo.get(it).modelo+","+arreglo.get(it).anio+","+arreglo.get(it).idConductor
            }

            doc.write(datacontenido)
            doc.flush()
            doc.close()
            return true
        } catch (io: IOException){ return false }
    }//guardarConducVehi

    fun guardarVehiConduc() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Vehiculo(this).consultarVehiculo()
            val arreglo2 = Conductor(this).consultarConduct()
            val doc = OutputStreamWriter(openFileOutput("docVehiConduc.txt", MODE_PRIVATE))

            //PARA EL VEHICULO
            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).id.toString()+","+arreglo.get(it).placa+","+arreglo.get(it).marca+","+arreglo.get(it).modelo+","+arreglo.get(it).anio+","+arreglo.get(it).idConductor
            }

            //PARA EL CONDUCTOR
            (0 until arreglo2.size).forEach {
                datacontenido += arreglo2.get(it).id.toString()+","+arreglo2.get(it).nombre+","+arreglo2.get(it).domicilio+","+arreglo2.get(it).noLicencia+","+arreglo2.get(it).vence
            }
            doc.write(datacontenido)
            doc.flush()
            doc.close()
            return true
        } catch (io: IOException){ return false }
    }//guardarVehiConduc

}