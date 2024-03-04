package com.example.examen_ib.model

import java.util.Date

class Cocinero(
    var codigoUnico: String = "",
    var nombre: String = "",
    var apellido: String = "",
    var edad: Int = 0,
    var fechaContratacion: String = "",
    var salario: Double = 0.0,
    @field:JvmField
    var isMainChef: Boolean = false
) {

    fun checkIsMainChef(isMainChef: Boolean): String{
        return if(isMainChef) "Si" else "No"
    }

    override fun toString(): String {
        return "\ncodigo unico es: $codigoUnico " +
                "\nCocinero $nombre $apellido. " +
                "\nEdad: $edad" +
                "\nContratado el $fechaContratacion " +
                "\nSalario de $salario" +
                "\n Chef principal: ${checkIsMainChef(isMainChef)}\n"
    }
}