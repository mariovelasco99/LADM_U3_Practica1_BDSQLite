package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*

//VISTA DEL CONDUCTOR
class MainActivity2 : AppCompatActivity() {
    var idConductores = ArrayList<Int>()
    var seleccionado = 0
    var actualizar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mostrarConductores()

        botonInsertarConductor.setOnClickListener {
            val conductor = Conductor(this)

            conductor.nombre = campoNombre.text.toString()
            conductor.domicilio = campoDomicilio.text.toString()
            conductor.noLicencia = campoNoLicencia.text.toString()
            conductor.vence = campoVence.text.toString()

            //Toast.makeText(this,"REALICE LA ACTUALIZACION",Toast.LENGTH_LONG).show()
            if(actualizar){
                val resultadoActualizar = conductor.actualizar(seleccionado.toString())
                actualizar = false

                if (resultadoActualizar){
                    Toast.makeText(this,"EXITO SE ACTUALIZO",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"ERROR!!  NO SE LOGRO ACTUALIZAR",Toast.LENGTH_LONG).show()
                }
                botonInsertarConductor.text = "INSERTAR"
                limpiarCampos()
                mostrarConductores()

            }else {
                val resultadoInsertar = conductor.insertar()
                if (resultadoInsertar){
                    Toast.makeText(this,"EXITO SE INSERTO",Toast.LENGTH_LONG).show()
                    limpiarCampos()
                    mostrarConductores()
                }else {
                    Toast.makeText(this,"ERROR!! NO SE LOGRO INSERTAR",Toast.LENGTH_LONG).show()
                }
            }
        }//botonInsertarConductor

        botonRegresarMenuPrin.setOnClickListener {
            finish()
        }//botonRegresarMenuPrin

    }//onCreate

    fun limpiarCampos(){
        campoNombre.setText("")
        campoDomicilio.setText("")
        campoNoLicencia.setText("")
        campoVence.setText("")
    }//limpiarCampos

    fun activarEvento(ListaDrivers : ListView){
        ListaDrivers.setOnItemClickListener { adapterView, view, i, l ->

            seleccionado= idConductores[i]
            AlertDialog.Builder(this)
                .setTitle("ATENCION!!")
                .setMessage("¿QUE DESEA HACER CON EL CONDUCTOR?")
                .setPositiveButton("EDITAR"){d,i-> modificar()}
                .setNegativeButton("ELIMINAR"){d,i-> borrar()}
                .setNeutralButton("CANCELAR"){d,i-> d.cancel()}
                .show()
        }
    }//activarEvento

    fun borrar(){
        AlertDialog.Builder(this)
            .setTitle("ATENCION!!")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR ID ${seleccionado}?")
            .setPositiveButton("SI"){d,i->
                val resultado = Conductor(this).eliminar(seleccionado)

                if (resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO",Toast.LENGTH_LONG).show()
                    mostrarConductores()
                } else {
                    Toast.makeText(this,"NO SE LOGRO ELIMINAR",Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }//borrar

    fun modificar(){
        actualizar = true
        botonInsertarConductor.text = "ACTUALIZAR"

        val conductor = Conductor(this).consultar(seleccionado.toString())
        campoNombre.setText(conductor.nombre)
        campoDomicilio.setText(conductor.domicilio)
        campoNoLicencia.setText(conductor.noLicencia)
        campoVence.setText(conductor.vence)
    }//modificar

    fun mostrarConductores(){
        val arrayConductor = Conductor(this).consultar()
        listaConductor.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayConductor)

        idConductores.clear()
        idConductores = Conductor(this).obtenerIDs()
        activarEvento(listaConductor)
    }//mostrarConductores
}