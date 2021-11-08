package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        botonLicenciasVencidas.setOnClickListener {
            consultarLicVencidas()
        }//botonLicenciasVencidas

        botonConductoresSnVehiculos.setOnClickListener {
            consultarCondSinVehiculo()
        }//botonConductoresSnVehiculos

        botonVehiculoAniosUsados.setOnClickListener {
            consultarVehiculoAnios()
        }//botonVehiculoAniosUsados

        botonAsignacionConducVehiculo.setOnClickListener {
            consultarCondVehAsignados()
        }//botonAsignacionConducVehiculo

        botonRegresarM.setOnClickListener {
            finish()
        }//botonRegresarM
    }//onCreate

    fun consultarLicVencidas(){
        val arrayConductor = Conductor(this).licenciaVencida()
        listaConsultas.adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayConductor)

    }//ConsultaLicenciasVencidas

    fun consultarVehiculoAnios(){
        var input = EditText(this)
        input.hint = "NUM DE AÑOS"
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("ATENCION!!")
            .setMessage("VEHICULOS QUE CUENTAN CON N CANTIDAD DE AÑOS")
            .setView(input)
            .setPositiveButton("ACEPTAR"){d,i->
                if(!input.text.isEmpty()){
                    val arrayConductor = Vehiculo(this).consultarVehiculosAniosUsados(input.text.toString().toInt())
                    listaConsultas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayConductor)
                }
            }
            .setNegativeButton("CENCELAR"){d,i-> d.cancel() }
            .show()
    }//consultarVehiculoAnios

    fun consultarCondSinVehiculo(){
        val arregloConductor = Conductor(this).sinAsignarVehiculo()
        listaConsultas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloConductor)

    }//ConsultasSinVehiculo

    fun consultarCondVehAsignados(){
        android.app.AlertDialog.Builder(this)
            .setTitle("ATENCION!!")
            .setMessage("¿QUE DESEA CONSULTAR?")
            .setPositiveButton("CONDUCTOR ASIGNADO AL VEHICULO"){d,i->
                mostrarVehAsignados(true)
            }
            .setNeutralButton("VEHICULO ASIGNADO AL CONDUCTOR"){d,i->
                mostrarVehAsignados(false)
            }
            .show()
    }//consultarCondVehAsignados

    fun mostrarVehAsignados(op:Boolean){
        var input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        if (op){
            input.hint = "ID CONDUCTOR"
        } else {
            input.hint = "ID VEHICULO"
        }
        android.app.AlertDialog.Builder(this)
            .setTitle("ATENCION!!")
            .setView(input)
            .setPositiveButton("ACEPTAR"){d,i->
                if (op){
                    if (!input.text.toString().isEmpty()) {
                        val arreglo =
                            Vehiculo(this).consultarVehiculosConductorAsig(input.text.toString())
                        listaConsultas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo)
                    }
                } else {
                    if (!input.text.toString().isEmpty()) {
                        //Conductor
                        val arreglo = Conductor(this).consultarConductVehiculo(input.text.toString())
                        listaConsultas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo)
                    }
                }
            }
            .setNegativeButton("CANCELAR"){d,i-> d.cancel()}
            .show()
    }//mostrarVehAsignados
}