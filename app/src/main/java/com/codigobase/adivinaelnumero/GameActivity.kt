package com.codigobase.adivinaelnumero

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var cantidadNumeros: Int = 0
    private val cantidadPorDefecto: Int = 4
    private var cadenaNumeroAdivinar: String = ""
    private val random: Random = Random
    private val listaRegistros: ArrayList<Registro> = ArrayList()
    private var listaNumeros: MutableList<Char> = mutableListOf()
    private var registroRepetido: Registro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Ocultar barra superior
        super.getSupportActionBar()?.hide()

        // Recibir la cantidad de valores de la otra actividad
        var valorRecibido = intent.extras?.getInt("cantidad")

        if (valorRecibido != null) {
            cantidadNumeros = valorRecibido
        } else {
            cantidadNumeros = cantidadPorDefecto
        }

        // Limitar el cuadro de texto a la cantidad de valores posibles
        txtNumeroIntroducido.filters = arrayOf(InputFilter.LengthFilter(cantidadNumeros))

        // Controlar los eventos y hacer un reset inicial
        eventos()
        resetInicial()
    }

    override fun onResume() {
        super.onResume()
        habiliarBotones(true)
    }

    private fun habiliarBotones(habilitar: Boolean) {
        btnHistorial.isEnabled = habilitar
        btnComprobar.isEnabled = habilitar
        btnRendirse.isEnabled = habilitar
        btnVolver.isEnabled = habilitar
    }

    private fun generarNumero() {
        this.cadenaNumeroAdivinar = ""
        var indiceAleatorio: Int

        for (i in 0 until cantidadNumeros) {
            indiceAleatorio = random.nextInt(0, listaNumeros.size)
            this.cadenaNumeroAdivinar += listaNumeros.removeAt(indiceAleatorio)
        }
    }

    private fun mostrarPrimeraPista() {
        lblNumeroOculto.text = String().padEnd(cantidadNumeros, '*')
        mostrarMensajePista(getCadena("pista"))
    }

    private fun eventos() {
        btnHistorial.setOnClickListener {
            habiliarBotones(false)
            mostrarHistorial()
        }
        btnComprobar.setOnClickListener { comprobar() }
        btnRendirse.setOnClickListener { rendirse() }
        btnJugarDeNuevo.setOnClickListener { jugarDeNuevo() }
        btnVolver.setOnClickListener { finish() }
    }

    private fun comprobar() {
        var cadenaNumeroIntroducido = txtNumeroIntroducido.text.toString()
        var aciertos: Int = 0
        var posibles: Int = 0

        // Primero se comprueba si se han introducido todos los numeros
        if (cadenaNumeroIntroducido.length == cantidadNumeros) {

            // A continuación se averigua si ya se ha usado dicho numero
            if (seHaUsado(cadenaNumeroIntroducido)) {
                mostrarMensajePista(getCadena("usado"))
                mostrarMensajeToast(getCadena("usadoToast"))

                // Despues se comprueba si hay numeros repetidos
            } else if (hayRepetidos(cadenaNumeroIntroducido)) {
                mostrarMensajes(getCadena("repetidos"))

                // Finalmente se hace un resumen con los numeros acertados y los posibles
            } else {
                for (i in 0..cantidadNumeros - 1) {
                    if (cadenaNumeroIntroducido.get(i) == cadenaNumeroAdivinar.get(i)) {
                        aciertos++
                    } else if (cadenaNumeroAdivinar.contains(cadenaNumeroIntroducido.get(i))) {
                        posibles++
                    }
                }

                // Si se han acertado todos los numeros has ganado, en caso contrario agrega un registro y muestra las pistas
                if (aciertos == cantidadNumeros) {
                    hasGanado()
                } else {
                    listaRegistros.add(Registro(cadenaNumeroIntroducido, aciertos, posibles))
                    mostrarMensajePista(getCadena("acierto", aciertos, posibles))
                    mostrarMensajeToast(getCadena("aciertoToast", aciertos, posibles))
                }
            }

            // Si no se han introducido todos los numeros, se mostrara cuantos valores faltan
        } else {
            var diferencia: Int = cantidadNumeros - cadenaNumeroIntroducido.length
            mostrarMensajes(getCadena("faltan", diferencia))
        }
    }


    // Este metodo manda un ArrayList<Registros> por el Bundle, recuerda que todos los elementos han de implementar Serializable
    private fun mostrarHistorial() {
        val intent =
            Intent(this, RegistrosActivity::class.java).apply {
                putExtra("registros", listaRegistros)
            }
        startActivity(intent)
    }

    private fun hayRepetidos(cadena: String): Boolean {
        var arrayCadena = cadena.toCharArray().toSet()
        return arrayCadena.size < cantidadNumeros
    }

    private fun seHaUsado(cadenaNumero: String): Boolean {
        for (registro in listaRegistros) {
            if (registro.getCadenaNumero().equals(cadenaNumero)) {
                this.registroRepetido = registro
                return true
            }
        }
        return false
    }

    private fun hasGanado() {
        setTitulo(getCadena("enhorabuena"))
        setCadenaNumOculto(cadenaNumeroAdivinar)
        var numIntentos: Int = listaRegistros.size
        mostrarMensajePista(getCadena("ganas", numIntentos))
        mostrarBotones(false)
    }

    private fun rendirse() {
        setTitulo(getCadena("eraEl"))
        mostrarMensajePista(getCadena("seSiente"))
        setCadenaNumOculto(cadenaNumeroAdivinar)
        mostrarBotones(false)
    }

    private fun jugarDeNuevo() {
        listaRegistros.clear()
        setTitulo(getCadena("tituloPorDefecto"))
        txtNumeroIntroducido.text.clear()
        mostrarBotones(true)
        resetInicial()
    }

    private fun resetInicial() {
        listaNumeros = "0123456789".toCharArray().toMutableList()
        generarNumero()
        mostrarPrimeraPista()
    }

    private fun mostrarBotones(mostrar: Boolean) {
        if (mostrar) {
            txtNumeroIntroducido.visibility = View.VISIBLE
            btnComprobar.visibility = View.VISIBLE
            btnRendirse.visibility = View.VISIBLE
            btnJugarDeNuevo.visibility = View.GONE
        } else {
            txtNumeroIntroducido.visibility = View.GONE
            btnRendirse.visibility = View.GONE
            btnComprobar.visibility = View.GONE
            btnJugarDeNuevo.visibility = View.VISIBLE
        }
    }

    private fun setTitulo(mensaje: String) {
        lblTitulo.text = mensaje
    }

    private fun setCadenaNumOculto(mensaje: String) {
        lblNumeroOculto.text = mensaje
    }

    private fun mostrarMensajePista(mensaje: String) {
        lblPista.text = mensaje
    }

    private fun mostrarMensajeToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensajes(mensaje: String) {
        mostrarMensajePista(mensaje)
        mostrarMensajeToast(mensaje)
    }

    // Obtiene una cadena completa con los datos, esto es porque no se me ha ocurrido aun la manera de complementar strings.xml con datos de la aplicacion
    private fun getCadena(nombreElemento: String, valor1: Int = 0, valor2: Int = 0): String {
        return when {
            nombreElemento.equals("pista") -> "De primeras solo puedo decirte que se compone de $cantidadNumeros números. ¿Aunque eso ya lo sabías no?"
            nombreElemento.equals("usado") -> "¡Eh! Que ese número ya ha sido usado, escribe otro.\n¿Te acuerdas? Tenía ${registroRepetido?.getAciertos()} aciertos y ${registroRepetido?.getPosibles()} posibles."
            nombreElemento.equals("usadoToast") -> "Ya has usado ese valor.\nTenía ${registroRepetido?.getAciertos()} aciertos y ${registroRepetido?.getPosibles()} posibles."
            nombreElemento.equals("repetidos") -> "Todos los números han de ser distintos."
            nombreElemento.equals("acierto") -> "Has acertado $valor1 y hay $valor2 posibles números.\nSigue intentándolo."
            nombreElemento.equals("aciertoToast") -> "$valor1 aciertos, $valor2 posibles"
            nombreElemento.equals("faltan") -> "Has de introducir los $cantidadNumeros números.\nTe quedan $valor1 números."
            nombreElemento.equals("ganas") -> "¡Has acertado de pleno!\nTan solo te ha llevado $valor1 intentos."
            nombreElemento.equals("enhorabuena") -> "¡Enhorabuena!"
            nombreElemento.equals("eraEl") -> "El número era"
            nombreElemento.equals("seSiente") -> "Lamentamos que no hayas acertado, en otra ocasión será."
            nombreElemento.equals("tituloPorDefecto") -> "Adivina el número"
            else -> "N/A"
        }
    }
}
