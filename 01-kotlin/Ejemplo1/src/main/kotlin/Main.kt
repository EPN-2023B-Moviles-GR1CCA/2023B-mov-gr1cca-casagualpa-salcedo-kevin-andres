fun main(){
    println("Hola mundo")
    //Declaracion de variables Inmutables
    val inmutable:String="Andres";
    //variable-> inmutable ="Andres"

    //Mutables (RE ASIGNAR )
    var mutable:String="Andres";
    mutable="Andres";

    var ejemploVariable ="Andres Casagualpa"
    //Variables primitivas
    val nombreProfesor:String="Adrian Eguez"
    val sueldo:Double=1.2
    val estadoCivil:Char='C'
    val mayorEdad:Boolean=true


    //val fechaNac:Date=Date()

    // SWITCH

    val estadoCivilWhen="C"
    when(estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else -> {
            println("No sabemos ")
        }
    }
    val coqueteo = if(estadoCivilWhen=="S") "Si" else "No"
    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00)
    //parametros nombrados
    calcularSueldo(sueldo = 10.00, bonoEspecial = 12.00, tasa = 20.00)


    val sumaUno=Suma(1,1)
    val sumaDos=Suma(null,1)
    val sumaTres=Suma(1,null)
    val sumaCuatro=Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    val respuestaForEach:Unit = arregloDinamico.
    forEach { valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }
    arregloDinamico.forEach{ println(it) }

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual:Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble()+100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map {it+15}

    val respuestaAny:Boolean = arregloDinamico
        .any{valorActual:Int ->
            return@any(valorActual>5)
        }
    println(respuestaAny)

    val respuestaAll:Boolean=arregloDinamico
        .all{ valorActual:Int->
            return@all(valorActual>5)
        }
    println(respuestaAll)

    val respuestaReduce: Int = arregloDinamico
        .reduce{acumulado:Int, valorActual:Int->
            return@reduce(acumulado+valorActual)
        }
    println(respuestaReduce)


    val respuestaFilter: List<Int> =arregloDinamico
        .filter { valorActual:Int->
            val mayoresACinco: Boolean = valorActual>5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it>5 }
    println(respuestaFilter)
    println(respuestaFilterDos)













}

abstract class NumerosJava{
    protected val numeroUno:Int
    protected val numeroDos:Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno=uno
        this.numeroDos=dos
        println("Inicializando")
    }
}

abstract class Numero(
    protected val numeroUno:Int,
    protected val numeroDos:Int
){
    init{
        this.numeroUno;this.numeroDos;
        numeroUno;numeroDos;
        println("Inicializando")
    }
}

class Suma(uno:Int, dos:Int):Numero(uno,dos){
    init {
        this.numeroUno; numeroUno;
        this.numeroDos;numeroDos;
    }
    constructor(
        uno:Int?,
        dos:Int
    ):this(
        if(uno==null) 0 else uno,dos
        ){
        numeroUno;
    }
    constructor(
        uno:Int,
        dos:Int?
    ): this(
        uno,
        if(dos==null) 0 else uno
    )

    constructor(
        uno:Int?,
        dos:Int?
    ): this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else uno
    )

public fun sumar():Int{
    val total=numeroUno+numeroDos
    agregarHistorial(total)
    return total
}
companion object{
    val pi =3.14
    fun elevarAlCuadrado(num:Int):Int{
        return num*num
    }
    val historialSumas = arrayListOf<Int>()
    fun agregarHistorial(valorNuevaSuma:Int){
        historialSumas.add(valorNuevaSuma)
    }
}

}


// void -> unit
fun imprimirNombre (nombre:String):Unit{
    println("Nombre: ${nombre}")
}
fun calcularSueldo(
    sueldo:Double,
    tasa:Double=12.00,
    bonoEspecial:Double?=null,
    //String?(nullable)
    //Date?(nullable)
    //Int?(nullable)
):Double{
    if(bonoEspecial==null){
        return sueldo*(100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo*(100/tasa)*bonoEspecial
    }
}

