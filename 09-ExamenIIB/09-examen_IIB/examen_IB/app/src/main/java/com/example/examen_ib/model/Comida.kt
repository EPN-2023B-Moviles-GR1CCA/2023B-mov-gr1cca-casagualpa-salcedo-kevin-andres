package com.example.examen_ib.model

import java.util.Date

class Comida(
    var identificador: String = "",
    var nombre: String = "",
    var fechaCreacion: String = "",
    @field:JvmField
    var isPlatoDelDia: Boolean = false,
    var tipoCocina: String = "",
    var cantidadProductos: Int = 0,
    var precio: Double = 0.0,
    var codigoUnicoCocinero: String = ""
) {

    fun checkEsPlatoDelDia(esPlatoDelDia: Boolean): String{
        return if(esPlatoDelDia) "Si" else "No"
    }

    override fun toString(): String {
        return "\nIdentificador: $identificador" +
                "\nComida: $nombre " +
                "\nCreado el $fechaCreacion " +
                "\n${checkEsPlatoDelDia(isPlatoDelDia)} es el plato del dia" +
                "\nPertenece a la cocina $tipoCocina " +
                "\ncontiene $cantidadProductos productos" +
                "\n y cuesta $$precio"
    }


}