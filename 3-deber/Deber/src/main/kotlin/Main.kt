import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import com.google.gson.Gson
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Selecciona una Opción:")
        println("1. Organizar Empleados")
        println("2. Organizar Departamentos")
        println("0. Salir")

        when (scanner.nextInt()) {
            1 -> {
                println("Administración de Empleados:")
                println("1. Ingresar Empleado")
                println("2. Mostrar Empleados")
                println("3. Actualizar Empleados")
                println("4. Borrar Empleados")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Ingrese los siguientes datos del Empleado:")
                        println("Nombre:")
                        val nombre = readLine()
                        println("Numero del Contrato:")
                        val numeroContrato = scanner.nextInt()
                        println("Salario:")
                        val salario = scanner.nextFloat()
                        println("Fecha de Ingreso (yyyy-mm-dd):")
                        val fechaFundacionString = readLine()
                        val formato = SimpleDateFormat("yyyy-mm-dd")
                        val fechaFundacion = formato.parse(fechaFundacionString)

                        println("Empleado Activo (true/false):")
                        val empleadoActivo = scanner.nextBoolean()
                        println("Responsabilidades del cargo separado por coma:")
                        val responsabilidades = scanner.next().split(",").toTypedArray()
                        println("Cargo:")
                        val cargo = scanner.next()

                        val empleado = Empleado(
                            Empleado.idDisponible(),
                            nombre,
                            numeroContrato,
                            salario,
                            fechaFundacion,
                            empleadoActivo,
                            responsabilidades,
                            cargo
                        )

                        Empleado.crearEmpleado(empleado)
                        println("Empleado creado con exito.")
                    }
                    2 -> {
                        val empleados = Empleado.observarEmpleados()
                        if (empleados.isNotEmpty()) {
                            println("Empleados:")
                            empleados.forEach { println(it) }
                        } else {
                            println("Empleados no encontrados.")
                        }
                    }
                    3 -> {
                        println("Empleados existentes:")
                        val empleadosExistentes = Empleado.observarEmpleados()
                        empleadosExistentes.forEach { println(it) }

                        println("\nIngrese el ID del Empleado a actualizar:")
                        val id = readLine()!!.toInt()
                        val empleadoToUpdate = empleadosExistentes.find { it.id == id }

                        if (empleadoToUpdate != null) {
                            println("Ingrese el atributo a actualizar (nombre, NumeroContrato, salario, fecha de ingreso, estado del empleado, resposabilidades del cargo, cargo):")
                            val atributo = readLine()

                            when (atributo) {
                                "nombre" -> {
                                    println("Nuevo nombre:")
                                    empleadoToUpdate.nombre = readLine()!!
                                }
                                "NumeroContrato" -> {
                                    println("Nuevo numero de contrato:")
                                    empleadoToUpdate.numeroContrato = readLine()!!.toInt()
                                }
                                "salario" -> {
                                    println("Nuevo salario:")
                                    empleadoToUpdate.salario = readLine()!!.toFloat()
                                }
                                "fecha de ingreso" -> {
                                    println("Fecha de ingreso (yyyy-mm-dd):")
                                    val creacionSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-mm-dd")
                                    empleadoToUpdate.creacion = formato.parse(creacionSF)
                                }
                                "estado del empleado" -> {
                                    println("El empoleado esta activo (true/false):")
                                    empleadoToUpdate.empleadoActivo = readLine()!!.toBoolean()
                                }
                                "resposabilidades del cargo" -> {
                                    println("Número de nuevas responsabilidades:")
                                    val numResponsabilidades = readLine()!!.toInt()
                                    val res = mutableListOf<String>()
                                    for (i in 1..numResponsabilidades) {
                                        println("Responsabilidad $i:")
                                        val responsabilidad = readLine()!!
                                        res.add(responsabilidad)
                                    }
                                    empleadoToUpdate.ingredientes = res.toTypedArray()
                                }
                                "cargo" -> {
                                    println("Nuevo cargo:")
                                    empleadoToUpdate.cargo = readLine()!!
                                }
                                else -> {
                                    println("Atributo no válido.")
                                    return
                                }
                            }

                            Empleado.actualizarEmpleado(empleadoToUpdate)
                            println("Empleado actualizado con exito.")
                        } else {
                            println("Empleado con el ID $id no encontrado.")
                        }

                    }
                    4 -> {
                        println("Emplpeados existentes:")
                        val empleadosExistentes = Empleado.observarEmpleados()
                        empleadosExistentes.forEach { println(it) }

                        println("Ingrese el ID del Empleado a eliminar:")
                        val id = scanner.nextInt()
                        Empleado.eliminarEmpleado(id)
                        println("Empleado borrado.")
                    }
                    else -> {
                        println("Opcion no valida")
                    }
                }
            }
            2 -> {
                println("Administración de los Departamentos:")
                println("1. Ingresar Departamentos")
                println("2. Mostrar Departamentos")
                println("3. Editar información del Departamento ")
                println("4. Eliminar Departamento")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Ingrese los siguientes datos del Departamento ")
                        println("Nombre del Departamento:")
                        val nombreDepartamento = readLine()
                        println("Cantidad de Empleados:")
                        val cantidadEmpleados = scanner.nextInt()
                        println("Presupuesto del Departamento: ")
                        val presupuesto = scanner.nextFloat()
                        println("Fecha de Creación (aaaa-mm-dd):")
                        val fechaFundacionString = readLine()
                        val formato = SimpleDateFormat("yyyy-mm-dd")
                        val fechaFundacion = formato.parse(fechaFundacionString)

                        println("Departamento Activo? (true/false):")
                        val departammentoActivo = scanner.nextBoolean()


                        val empleadosEnDepartamento = mutableListOf<Empleado>()
                        while (true) {
                            println("Desea agregar un nuevo Empleado? (y/n)")
                            val choice = scanner.next()
                            if (choice.equals("y", ignoreCase = true)) {
                                val empleadosExistentes = Empleado.observarEmpleados()
                                empleadosExistentes.forEach { println(it) }
                                println("Ingrerse el Id del empleado para agregar:")
                                val empleadoId = scanner.nextInt()
                                val empleadoToAdd = Empleado.observarEmpleados().find { it.id == empleadoId }
                                if (empleadoToAdd != null) {
                                    empleadosEnDepartamento.add(empleadoToAdd)
                                    println("Empleado agregado exitosamente.")
                                } else {
                                    println("El empleado con el el ID $empleadoId no ha sido encontrado.")
                                }
                            } else {
                                break
                            }
                        }

                        val departamento = Departamento(
                            Departamento.idDisponible(),
                            nombreDepartamento,
                            cantidadEmpleados,
                            presupuesto,
                            fechaFundacion,
                            departammentoActivo,
                            empleadosEnDepartamento.toTypedArray()
                        )

                        Departamento.crearCocinero(departamento)
                        println("Departamento guardado.")

                    }
                    2 -> {
                        val departamentos = Departamento.observarCocinero()
                        if (departamentos.isNotEmpty()) {
                            println("Departamentos:")
                            departamentos.forEach { println(it) }
                        } else {
                            println("Departamento no encontrado.")
                        }
                    }
                    3 -> {
                        println("Departamentos existentes:")
                        val cocinerosExistentes = Departamento.observarCocinero()
                        cocinerosExistentes.forEach { println(it) }

                        println("\nIngrese el ID del departamento para actualizar:")
                        val id = readLine()!!.toInt()
                        val departamentoToUpdate = cocinerosExistentes.find { it.id == id }

                        if (departamentoToUpdate != null) {
                            println("Ingrese el atributo a actualizar (nombre del departamento, cantidad de empleados, presupuesto, fecha de fundacion, estado del departamento, empleados en el departamento):")
                            val atributo = readLine()

                            when (atributo) {
                                "nombre del departamento" -> {
                                    println("Nuevo nombreDepartamento:")
                                    departamentoToUpdate.nombreDepartamento = readLine()!!
                                }
                                "cantidad de empleados" -> {
                                    println("Nueva cantidad de empleados:")
                                    departamentoToUpdate.cantidadEmpleados = readLine()!!.toInt()
                                }
                                "presupuesto" -> {
                                    println("Nuevo presupuesto:")
                                    departamentoToUpdate.presupuesto = readLine()!!.toFloat()
                                }
                                "fecha de fundacion" -> {
                                    println("Fecha de fundacion(yyyy-mm-dd):")
                                    val integracionSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-mm-dd")
                                    departamentoToUpdate.fechaFundacion = formato.parse(integracionSF)
                                }
                                "estado del departamento" -> {
                                    println("El estado del departamento es? (true/false):")
                                    departamentoToUpdate.departamentoActivo = readLine()!!.toBoolean()
                                }
                                "empleados en el departamento" -> {
                                    println("Número de empleados:")
                                    val numEmpleados = readLine()!!.toInt()
                                    val empleados = mutableListOf<Empleado>()
                                    for (i in 1..numEmpleados) {
                                        println("Empleado $i:")
                                        val empleadoId = readLine()!!.toInt()
                                        val empleado = Empleado.observarEmpleados().find { it.id == empleadoId }
                                        if (empleado != null) {
                                            empleados.add(empleado)
                                        }
                                    }
                                    departamentoToUpdate.empleados = empleados.toTypedArray()
                                }
                                else -> {
                                    println("Atributo no válido.")
                                    return
                                }
                            }

                            Departamento.actualizarCocinero(departamentoToUpdate)
                            println("Departamento actualizado.")
                        } else {
                            println("El Departamento con la ID $id indicada no existe.")
                        }

                    }
                    4 -> {
                        println("Departamentos existentes:")
                        val cocinerosExistentes = Departamento.observarCocinero()
                        cocinerosExistentes.forEach { println(it) }

                        println("Ingrese el Id del departamento a eliminar:")
                        val id = scanner.nextInt()
                        Departamento.eliminarCocinero(id)
                        println("Departamento borrado exitosamente")
                    }
                    else -> {
                        println("Opcion invalida.")
                    }
                }
            }
            0 -> {
                println("Saliendo, Muchas gracias por usar el sistema")
                return
            }
            else -> {
                println("Opcion invalida.")
            }
        }
    }
}



data class Empleado(
    val id: Int,
    var nombre: String?,
    var numeroContrato: Int,
    var salario: Float,
    var creacion: Date,
    var empleadoActivo: Boolean,
    var ingredientes: Array<String>,
    var cargo: String
) {
    companion object {
        private const val archivo_empleados = "C:\\Users\\LENOVO\\Desktop\\Aplicaciones moviles\\2023B-mov-gr1cca-casagualpa-salcedo-kevin-andres\\3-deber\\Deber\\src\\main\\kotlin\\empleado.txt"
        fun idDisponible(): Int {
            val empleados = observarEmpleados()
            return empleados.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun crearEmpleado(empleado: Empleado) {
            val empleados = observarEmpleados().toMutableList()
            empleados.add(empleado)
            guardarEmpleado(empleados)
        }

        fun guardarEmpleado(empleados: List<Empleado>) {
            val archivoEmpleados = File(archivo_empleados)
            val gson = Gson()
            val lineas = empleados.map { gson.toJson(it) }
            archivoEmpleados.writeText(lineas.joinToString("\n"))
        }
        
        fun observarEmpleados(): List<Empleado> {
            val archivoEmpleados = File(archivo_empleados)
            val gson = Gson()
            val lineas = archivoEmpleados.readLines()
            val empleados = mutableListOf<Empleado>()

            for (linea in lineas) {
                val empleado = gson.fromJson(linea, Empleado::class.java)
                empleados.add(empleado)
            }

            return empleados
        }
        fun actualizarEmpleado(empleado: Empleado) {
            val empleados = Empleado.observarEmpleados().toMutableList()
            val index = empleados.indexOfFirst { it.id == empleado.id }
            if (index != -1) {
                empleados[index] = empleado
                Empleado.guardarEmpleado(empleados)
            }
        }

        fun eliminarEmpleado(id: Int) {
            val empleados = observarEmpleados().toMutableList()
            val empleadoBorrar = empleados.find { it.id == id }
            if (empleadoBorrar != null) {
                empleados.remove(empleadoBorrar)
                guardarEmpleado(empleados)
            }
        }
        
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-mm-dd")
        val fecha = formato.format(creacion)
        return """
            Empleado #$id
            Nombre: $nombre
            Fecha de ingreso: $fecha
            Numero de Contrato: $numeroContrato
            Salario: $salario
            Empleado Activo: $empleadoActivo
            Responsabilidades del cargo: ${ingredientes.joinToString(", ")}
            Cargo: $cargo
        """.trimIndent()
    }
}


data class Departamento(
    val id: Int,
    var nombreDepartamento: String?,
    var cantidadEmpleados: Int,
    var presupuesto: Float,
    var fechaFundacion: Date,
    var departamentoActivo: Boolean,
    var empleados: Array<Empleado>
) {
    companion object {
        private const val archivo_cocineros = "C:\\Users\\LENOVO\\Desktop\\Aplicaciones moviles\\2023B-mov-gr1cca-casagualpa-salcedo-kevin-andres\\3-deber\\Deber\\src\\main\\kotlin\\departamento.txt"

        fun observarCocinero(): List<Departamento> {
            val archivoCocineros = File(archivo_cocineros)
            val gson = Gson()
            val lineas = archivoCocineros.readLines()
            val departamentos = mutableListOf<Departamento>()

            for (linea in lineas) {
                val departamento = gson.fromJson(linea, Departamento::class.java)
                departamentos.add(departamento)
            }

            return departamentos
        }

        fun crearCocinero(departamento: Departamento) {
            val departamentos = observarCocinero().toMutableList()
            departamentos.add(departamento)
            guardarCocinero(departamentos)
        }

        fun idDisponible(): Int {
            val departamentos = observarCocinero()
            return departamentos.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun eliminarCocinero(id: Int) {
            val departamentos = observarCocinero().toMutableList()
            val cocineroBorrar = departamentos.find { it.id == id }
            if (cocineroBorrar != null) {
                departamentos.remove(cocineroBorrar)
                guardarCocinero(departamentos)
            }
        }

        private fun guardarCocinero(departamentos: List<Departamento>) {
            val archivoCocineros = File(archivo_cocineros)
            val gson = Gson()
            val lineas = departamentos.map { gson.toJson(it) }
            archivoCocineros.writeText(lineas.joinToString("\n"))
        }

        fun actualizarCocinero(departamento: Departamento) {
            val departamentos = observarCocinero().toMutableList()
            val index = departamentos.indexOfFirst { it.id == departamento.id }
            if (index != -1) {
                departamentos[index] = departamento
                guardarCocinero(departamentos)
            }
        }
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-mm-dd")
        val fecha = formato.format(fechaFundacion)
        return "Departamento #$id \nNombre: $nombreDepartamento \nCantidad de Empleados: $cantidadEmpleados \nPresupuesto: $presupuesto\nFecha de creación: $fechaFundacion\nDepartamento Activo: $departamentoActivo\nEmpleados: ${empleados.contentToString()}\n"
    }
}