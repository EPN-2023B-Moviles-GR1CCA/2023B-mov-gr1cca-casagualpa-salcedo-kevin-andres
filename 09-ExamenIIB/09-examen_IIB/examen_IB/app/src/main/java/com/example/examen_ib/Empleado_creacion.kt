package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.examen_ib.model.Comida
import com.google.android.material.snackbar.Snackbar

class Empleado_creacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_creacion)

        val codigoUnicoCocinero = intent.extras?.getString("codigoUnico")

        /*Definicion del combo box para plato dia*/
        val spinnerPlatoDia = findViewById<Spinner>(R.id.sp_plato_dia)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_chef_principal,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerPlatoDia.adapter = adaptador
        /*Fin definicion spinner*/

        /*Definicion del combo box para tipo de cocina*/
        val spinnerTipoCocina = findViewById<Spinner>(R.id.sp_tipo_cocina)

        val adaptadorTipoCocina = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_tipo_cocina,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerTipoCocina.adapter = adaptadorTipoCocina
        /*Fin definicion spinner*/

        /*Creacion de comida*/
        val btnGuardarComida = findViewById<Button>(R.id.btn_crear_plato)
        btnGuardarComida
            .setOnClickListener {
                try {
                    val identificador = findViewById<EditText>(R.id.et_identificador)
                    val nombre = findViewById<EditText>(R.id.et_nombre)
                    val fechaCreacion = findViewById<EditText>(R.id.et_fecha)
                    val esPlatoDelDia = spinnerPlatoDia.selectedItem.toString()
                    val tipoCocina = spinnerTipoCocina.selectedItem.toString()
                    val cantidadProductos = findViewById<EditText>(R.id.et_cantidad_productos)
                    val precio = findViewById<EditText>(R.id.et_precio)

                    // Limpiar errores anteriores
                    identificador.error = null
                    nombre.error = null
                    cantidadProductos.error = null
                    precio.error = null
                    fechaCreacion.error = null

                    if(validarCampos(identificador, nombre, fechaCreacion, esPlatoDelDia, tipoCocina, cantidadProductos, precio)){
                        val esPlatoDia = esPlatoDelDia.equals("Si")
                        val newFood = Comida(
                            identificador.text.toString(),
                            nombre.text.toString(),
                            fechaCreacion.text.toString(),
                            esPlatoDia,
                            tipoCocina,
                            cantidadProductos.text.toString().toInt(),
                            precio.text.toString().toDouble(),
                            codigoUnicoCocinero!!
                        )

                        BDD.bddAplicacion!!.crearComida(newFood)
                            .addOnSuccessListener {
                                val data = Intent()
                                data.putExtra("codigoCocinero", codigoUnicoCocinero)
                                data.putExtra("message", "La comida se ha creado exitosamente")
                                setResult(RESULT_OK, data)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                mostrarSnackbar("Hubo un problema al crear la comida")
                            }
                    }

                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_creacion_plato), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun validarCampos(identificador: EditText, nombre: EditText, fecha: EditText, esPlatoDelDia: String, tipoCocina: String, cantidadProductos: EditText, precio: EditText): Boolean{
        if (identificador.text.isBlank()) {
            identificador.error = "Campo requerido"
            return false
        }

        if (nombre.text.isBlank()) {
            nombre.error = "Campo requerido"
            return false
        }

        if (cantidadProductos.text.isBlank()) {
            cantidadProductos.error = "Campo requerido"
            return false
        } else {
            val cantidadProductosInt = cantidadProductos.text.toString().toInt()
            if (cantidadProductosInt < 3) {
                cantidadProductos.error = "La cantidad de productos debe ser un número mayor o igual a 3"
                return false
            }
        }

        if (fecha.text.isBlank()) {
            fecha.error = "Campo requerido"
            return false
        } else {
            val fechaRegex = Regex("""^\d{4}-\d{2}-\d{2}$""")
            if (!fechaRegex.matches(fecha.text.toString())) {
                fecha.error = "Formato de fecha inválido (debe ser yyyy-MM-dd)"
                return false
            }
        }

        if(precio.text.isBlank()){
            precio.error = "Campo requerido"
            return false
        }else{
            val precioDouble = precio.text.toString().toDouble()
            if (precioDouble <=0) {
                precio.error = "El precio debe ser un número mayor a 0"
                return false
            }
        }

        if(esPlatoDelDia.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique si es el plato del día o no")
            return false
        }

        if(tipoCocina.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique el tipo de cocina")
            return false
        }
        return true
    }
}