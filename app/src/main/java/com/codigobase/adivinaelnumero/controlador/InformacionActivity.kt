package com.codigobase.adivinaelnumero.controlador

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.adivinaelnumero.R
import kotlinx.android.synthetic.main.activity_informacion.*

class InformacionActivity : AppCompatActivity() {

    private var esAcercaDe: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion)

        // Ocultar barra superior
        super.getSupportActionBar()?.hide()

        // Obtener los valores
        this.esAcercaDe = intent.extras?.getBoolean("esAcercaDe")!!

        // Mostrar la informacion correspondiente
        if (esAcercaDe) {
            lblTitulo.text = getString(R.string.btn_acerca)
            lblInformacion.text = getString(R.string.acerca_de)
            btnCodigo.visibility = View.VISIBLE
            imgLogo.visibility = View.VISIBLE
        } else {
            lblTitulo.text = getString(R.string.btn_instrucciones)
            lblInformacion.text = getString(R.string.instrucciones)
        }

        // Evento del boton codigo
        btnCodigo.setOnClickListener { mostrarWeb() }

        // Evento del boton volver
        btnVolver.setOnClickListener { finish() }
    }

    private fun mostrarWeb() {
        val paginaWeb = "https://github.com/CarlosAguirreV/AdivinaElNumero"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paginaWeb))
        startActivity(intent)
    }
}
