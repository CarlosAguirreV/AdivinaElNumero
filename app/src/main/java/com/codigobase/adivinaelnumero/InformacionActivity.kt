package com.codigobase.adivinaelnumero

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        } else {
            lblTitulo.text = getString(R.string.btn_instrucciones)
            lblInformacion.text = getString(R.string.instrucciones)
        }

        // Evento del boton volver
        btnVolver.setOnClickListener(View.OnClickListener { finish() })
    }
}
