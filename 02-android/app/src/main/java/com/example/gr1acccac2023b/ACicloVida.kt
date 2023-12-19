package com.example.gr1acccac2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar
import androidx.compose.material3.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal=""
    fun mostrarSnackbar(texto: String){
        textoGlobal=textoGlobal+""+texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),//view
                textoGlobal, // texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }
    override fun onStart(){
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume(){
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onRestart(){
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause(){
        super.onPause()
        mostrarSnackbar("onStart")
    }

    override fun onStop(){
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            //Aqui se puede guardar las variables
            //Primitivos
            putString("textoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //Recupera las variables
        //primitivos
        val textoRecuperado:String? = savedInstanceState
            .getString("textoGuardado")
        //val textoRecuperado :Int?= savedInstanceState.getInt("xx")
        if(textoRecuperado!= null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal=textoRecuperado
        }
    }


}