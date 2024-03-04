package com.example.examen_ib

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen_ib.model.Cocinero
import com.example.examen_ib.repository.DepartamentoFirestore
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var cocineros = arrayListOf<Cocinero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        //Inicializacion de la base de datos
        BDD.bddAplicacion = DepartamentoFirestore()

        BDD.bddAplicacion!!.obtenerCocineros { cocineros ->
            this.cocineros = cocineros

            if(cocineros.size != 0){
                //listado de cocineros
                val listView = findViewById<ListView>(R.id.lv_list_cocineros)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    cocineros
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }else{
                mostrarSnackbar("No existen cocineros")
            }
        }

        val btnCrearCocinero = findViewById<Button>(R.id.id_btn_crear)
        btnCrearCocinero
            .setOnClickListener {
                val intent = Intent(this, Departamento_creacion::class.java)
                callbackContenidoIntentExplicito.launch(intent)
            }
    }//termina onCreate

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_cocineros), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    //creacion de las opciones de accion (editar, eliminar, ver comidas)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val codigoUnico = cocineros.get(posicionItemSeleccionado).codigoUnico
                val nombre_Cocinero = cocineros.get(posicionItemSeleccionado).nombre + " " + cocineros.get(posicionItemSeleccionado).apellido
//                mostrarSnackbar(codigoUnico)
                val extras = Bundle()
                extras.putString("codigoUnico", codigoUnico)
                extras.putString("nombreCocinero", nombre_Cocinero)
                irEdicionCocinero(Ddepartamento_edicion::class.java, extras)
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar(cocineros.get(posicionItemSeleccionado).codigoUnico)
                val result: Boolean = abrirDialogo(cocineros.get(posicionItemSeleccionado).codigoUnico)
                if(result) true else

                return false
            }
            R.id.mi_ver_comida -> {
                val codigoUnico = cocineros.get(posicionItemSeleccionado).codigoUnico
                val nombre_Cocinero = cocineros.get(posicionItemSeleccionado).nombre + " " + cocineros.get(posicionItemSeleccionado).apellido
                mostrarSnackbar(codigoUnico)
                val extras = Bundle()
                extras.putString("codigoUnico", codigoUnico)
                extras.putString("nombreCocinero", nombre_Cocinero)
                irEdicionCocinero(Empleado_Listado::class.java, extras)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(codigoUnico: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar a este cocinero?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                BDD.bddAplicacion?.eliminarDepartamentoPorCodigoUnico(codigoUnico)
                    ?.addOnSuccessListener {
                        mostrarSnackbar("Cocinero eliminado exitosamente")
                        cargarListaCocineros()  // Actualiza la lista después de la eliminación
                        eliminacionExitosa = true
                    }
                    ?.addOnFailureListener { e ->
                        mostrarSnackbar("No se pudo eliminar al cocinero")
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

    fun irEdicionCocinero(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
//        startActivity(intent)
        callbackContenidoIntentExplicito.launch(intent)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarListaCocineros()
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }

    private fun cargarListaCocineros() {
        // Cargar la lista de cocineros desde la base de datos y notificar al adaptador
        BDD.bddAplicacion!!.obtenerCocineros { cocineros ->
            this.cocineros = cocineros

            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cocineros
            )
            val listView = findViewById<ListView>(R.id.lv_list_cocineros)
            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
        }
    }

}