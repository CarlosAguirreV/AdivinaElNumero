package com.codigobase.adivinaelnumero

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // En cuanto cargue esperar un poco y quitar el Splash Screen
        Thread.sleep(100)
        setTheme(R.style.AppTheme)

        // Del onCreate
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar barra superior
        super.getSupportActionBar()?.hide()

        // Definir el texto de informacion inferior y el texto asociado a la barra
        lblInfo.text = "Selecciona un valor de 2 a 4 y pulsa Jugar."
        lblCantidad.text = "${sldCantidad.getProgress() + 2} Números"

        // Eventos que se ejecutaran
        eventos()
    }

    override fun onResume() {
        super.onResume()
        habiliarBotones(true)
    }

    private fun habiliarBotones(habilitar: Boolean) {
        btnJugar.isEnabled = habilitar
        btnInstrucciones.isEnabled = habilitar
        btnAcercaDe.isEnabled = habilitar
        btnSalir.isEnabled = habilitar
    }

    private fun eventos() {

        // Al cambiar el valor de la barra deslizadora
        sldCantidad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                lblCantidad.text = "${progress + 2} Números"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        btnJugar.setOnClickListener(View.OnClickListener {
            habiliarBotones(false)
            mostrarJuego()
        })
        btnInstrucciones.setOnClickListener(View.OnClickListener {
            habiliarBotones(false)
            mostrarInstrucciones()
        })
        btnAcercaDe.setOnClickListener(View.OnClickListener {
            habiliarBotones(false)
            mostrarAcercaDe()
        })
        btnSalir.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun mostrarJuego() {
        val intent =
            Intent(this, GameActivity::class.java).apply {
                putExtra("cantidad", sldCantidad.getProgress() + 2)
            }
        startActivity(intent)
    }

    private fun mostrarInstrucciones() {
        val intent =
            Intent(this, InformacionActivity::class.java).apply {
                putExtra("esAcercaDe", false)
            }
        startActivity(intent)
    }

    private fun mostrarAcercaDe() {
        val intent =
            Intent(this, InformacionActivity::class.java).apply {
                putExtra("esAcercaDe", true)
            }
        startActivity(intent)
    }
}
