package mx.tecnm.tepic.ladm_u3_practica1_bdsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuprincipal.setOnItemClickListener { adapterView, view, i, l ->
            when(i){
                0 ->{
                    var activity2 = Intent(this,MainActivity2::class.java)
                    startActivity(activity2)
                }/*Opcion Conductor*/
                1 ->{
                    var activity3 = Intent(this,MainActivity3::class.java)
                    startActivity(activity3)
                }/*Opcion Vehiculo*/
                2 ->{
                    var activity4 = Intent(this,MainActivity4::class.java)
                    startActivity(activity4)
                }/*Opcion Consultas*/
                3 ->{
                    var activity5 = Intent(this,MainActivity5::class.java)
                    startActivity(activity5)
                }/*Opcion Exportacion*/
                4 ->{
                    finish()
                }/*Opcion Salir*/
            }
        }
    }
}