package com.example.examen_ib

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen_ib.model.Empleado
import com.example.examen_ib.repository.CocineroRepository
import com.google.android.material.snackbar.Snackbar

class Departamento_Listado : AppCompatActivity() {

    var empleados = arrayListOf<Empleado>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_listado)

        //Inicializacion de la base de datos
        BDD.bddAplicacion = CocineroRepository(this)

        val codigoUnicoCocinero = intent.extras?.getString("codigoUnico")
        val nombre_cocinero = intent.extras?.getString("nombreCocinero")

        findViewById<TextView>(R.id.txt_titulo_nombre_cocinero).setText(nombre_cocinero)

        if(codigoUnicoCocinero != null){
            empleados = BDD.bddAplicacion!!.obtenerComidasPorCocinero(codigoUnicoCocinero)
            if(empleados.size != 0){
                //listado de comidas
                val listView = findViewById<ListView>(R.id.lv_listados_comidas)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    empleados
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }else{
                mostrarSnackbar("No existen comidas")
            }

            val btnCrearComida = findViewById<Button>(R.id.btn_crear_comida)
            btnCrearComida
                .setOnClickListener {
                    val extras = Bundle()
                    extras.putString("codigoUnico", codigoUnicoCocinero)
                    irActividad(Empleado_creacion::class.java, extras)
                }
        }

    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_comidas), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun irActividad(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
//        startActivity(intent)
        callbackContenidoIntentExplicito.launch(intent)
    }

    //creacion de las opciones de accion (editar, eliminar)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_comidas, menu)
        //obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_comida ->{
                val identificador = empleados.get(posicionItemSeleccionado).identificador
                val nombre_Comida = empleados.get(posicionItemSeleccionado).nombre
                val codigoCocinero = empleados.get(posicionItemSeleccionado).codigoUnicoCocinero
//                mostrarSnackbar(identificador)
                val extras = Bundle()
                extras.putString("identificador", identificador)
                extras.putString("codigoCocinero", codigoCocinero)
                extras.putString("nombreComida", nombre_Comida)
                irActividad(Empleado_edicion::class.java, extras)
                return true
            }
            R.id.mi_eliminar_comida -> {
                val identificador = empleados.get(posicionItemSeleccionado).identificador
                val codigoCocinero = empleados.get(posicionItemSeleccionado).codigoUnicoCocinero
//                mostrarSnackbar(identificador)
                val result: Boolean = abrirDialogo(identificador, codigoCocinero)
                if(result) true else

                    return false
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(codigoComida: String, codigoCocinero: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar esta comida?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                val respuesta = BDD.bddAplicacion?.eliminarComidaPorIdentificadorYCodigoCocinero(codigoComida, codigoCocinero)

                if (respuesta == true) {
                    mostrarSnackbar("Comida eliminado exitosamente")
                    cargarListaComidas(codigoCocinero)
                    eliminacionExitosa = true
                } else {
                    mostrarSnackbar("No se pudo eliminar esta comida")
                    eliminacionExitosa = false
                }
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()

        return eliminacionExitosa
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarListaComidas("${data?.getStringExtra("codigoCocinero")}")
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }

    private fun cargarListaComidas(codigoCocinero: String) {
        // Cargar la lista de comidas del cocinero desde la base de datos y notificar al adaptador
        empleados = BDD.bddAplicacion!!.obtenerComidasPorCocinero(codigoCocinero)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            empleados
        )
        val listView = findViewById<ListView>(R.id.lv_listados_comidas)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
}