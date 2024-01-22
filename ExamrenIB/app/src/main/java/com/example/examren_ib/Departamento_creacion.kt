package com.example.examren_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class Departamento_creacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento_creacion)

        val spinnerDepartamentoActivo= findViewById<Spinner>(R.id.sp_dep_activo)

        val adaptador =ArrayAdapter.createFromResource(
            this,
            R.array.DepartamentoActivo,
            android.R.layout.simple_list_item_1
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerDepartamentoActivo.adapter=adaptador


    }
}