package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*

//VISTA DEL VEHICULO
class MainActivity3 : AppCompatActivity() {
    var idVehiculo = ArrayList<Int>()
    var seleccionado = 0
    var actualizar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        mostrarVehiculos()

        botonInsertarVehiculo.setOnClickListener {
            val vehiculo = Vehiculo(this)

            vehiculo.placa = campoPlaca.text.toString()
            vehiculo.marca = campoMarca.text.toString()
            vehiculo.modelo = campoModelo.text.toString()
            vehiculo.anio = campoAnio.text.toString().toInt()
            vehiculo.idConductor = campoIdConductor.text.toString().toInt()

            if (actualizar){
                val resultadoActualizar = vehiculo.actualizar(seleccionado.toString())
                actualizar = false

                if (resultadoActualizar){
                    Toast.makeText(this,"EXITO SE ACTUALIZO",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this,"ERROR!! NO SE ACTUALIZO", Toast.LENGTH_LONG).show()
                }
                botonInsertarVehiculo.text = "INSERTAR"
                mostrarVehiculos()
                limpiarCampos()
            }else {
                val resultadoInsertar = vehiculo.insertar()

                if(resultadoInsertar){
                    Toast.makeText(this,"EXITO SE INSERTO",Toast.LENGTH_LONG).show()
                    mostrarVehiculos()
                    limpiarCampos()

                }else {
                    Toast.makeText(this,"ERROR!! NO SE INSERTO", Toast.LENGTH_LONG).show()
                }
            }
        }//botonInsertarVehiculo

        botonMenuP.setOnClickListener{
            finish()
        }//botonMenuP

    }//onCreate

    fun mostrarVehiculos(){
        val arrayVehiculo = Vehiculo(this).consultar()
        listaVehiculo.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayVehiculo)

        idVehiculo.clear()
        idVehiculo = Vehiculo(this).obtenerIDs()
        activarEvento(listaVehiculo)
    }//mostrarVehiculos

    fun limpiarCampos(){
        campoPlaca.setText("")
        campoMarca.setText("")
        campoModelo.setText("")
        campoAnio.setText("")
        campoIdConductor.setText("")
    }//limpiarCampos

    fun activarEvento(ListaCars:ListView){
        ListaCars.setOnItemClickListener { adapterView, view, i, l ->

            seleccionado = idVehiculo[i]
            AlertDialog.Builder(this)
                .setTitle("ATENCION!!")
                .setMessage("¿QUE DESEA HACER CON EL VEHICULO?")
                .setPositiveButton("EDITAR"){d,i-> modificar() }
                .setNegativeButton("ELIMINAR"){d,i-> borrar() }
                .setNeutralButton("CANCELAR"){d,i-> d.cancel()}
                .show()
        }
    }//activarEvento


    fun borrar(){
        AlertDialog.Builder(this)
            .setTitle("ALERTA!!")
            .setMessage("¿DESEAS BORRAR ID ${seleccionado}?")
            .setPositiveButton("SI"){d,i->
                val resultado = Vehiculo(this).eliminar(seleccionado)

                if (resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO",Toast.LENGTH_LONG).show()
                    mostrarVehiculos()
                } else {
                    Toast.makeText(this,"NO SE LOGRO ELIMINAR",Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }//borrar

    fun modificar(){
        actualizar = true
        botonInsertarVehiculo.text = "ACTUALIZAR"
        val vehiculo = Vehiculo(this).consultar(seleccionado.toString())

        campoPlaca.setText(vehiculo.placa)
        campoMarca.setText(vehiculo.marca)
        campoModelo.setText(vehiculo.modelo)
        campoAnio.setText(vehiculo.anio.toString())
        campoIdConductor.setText(vehiculo.idConductor.toString())
    }//modificar

}