package com.example.examen_ib.model

class Empleado(
    var identificador: String,
    var nombre: String,
    var fechaCreacion: String,
    var esPlatoDelDia: Boolean,
    var tipoCocina: String,
    var cantidadProductos: Int,
    var precio: Double,
    var codigoUnicoCocinero: String
) {

    fun checkEsPlatoDelDia(esPlatoDelDia: Boolean): String{
        return if(esPlatoDelDia) "Si" else "No"
    }

    override fun toString(): String {
        return "\nIdentificador: $identificador" +
                "\nNombre:: $nombre " +
                "\nFecha de contrato: $fechaCreacion " +
                "\n${checkEsPlatoDelDia(esPlatoDelDia)}  pertenece a un departamento" +
                "\nNacionalidad $tipoCocina " +
                "\nPrecio por hora extra: $cantidadProductos " +
                "\nSalario: $$precio"
    }


}