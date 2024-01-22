package com.example.examren_ib

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.examren_ib.ui.theme.ExamrenIBTheme
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val btnCrearDepartamento=findViewById<Button>(R.id.id_btn_crear)
        btnCrearDepartamento
            .setOnClickListener {
            irActividad(Departamento_creacion::class.java)
            }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_departamentos),
                texto,
                Snackbar.LENGTH_LONG
            )
            .show()
    }
}

