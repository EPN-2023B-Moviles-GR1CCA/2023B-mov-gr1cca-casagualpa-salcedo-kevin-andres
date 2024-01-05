package com.example.gr1acccac2023b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.compose.material3.Snackbar
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre =intent.getStringExtra("nombre")
        val apellido =intent.getStringExtra("apellido")
        val edad =intent.getIntExtra("edad",0)
        mostrarSnackbar("${nombre} ${apellido} ${edad}")
        val boton =findViewById<Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener { devolverRespuesta() }
    }
    fun devolverRespuesta(){
        val intentDevolverParametros=Intent()
        intentDevolverParametros.putExtra("nombreModificado","Pedro")
        intentDevolverParametros.putExtra("edadModificado",33)
        setResult(
            RESULT_OK, //resultado ok
            intentDevolverParametros //variables de intent
        )
        finish() //cerramos la actividad
    }
    fun mostrarSnackbar(texto: String){
        Snackbar
            .make(
                findViewById(R.id.id_layout_intents),
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
    }





}