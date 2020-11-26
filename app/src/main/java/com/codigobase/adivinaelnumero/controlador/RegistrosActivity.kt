package com.codigobase.adivinaelnumero.controlador

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.adivinaelnumero.R
import com.codigobase.adivinaelnumero.modelo.Registro
import kotlinx.android.synthetic.main.activity_registros.*

class RegistrosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        // Ocultar barra superior
        super.getSupportActionBar()?.hide()

        // Recibir registros y mostrarlos si los hay
        var valorRecibido = intent.extras?.getSerializable("registros")

        if (valorRecibido != null) {
            var registros: ArrayList<Registro> = valorRecibido as ArrayList<Registro>

            if (registros.size > 0) {
                lblTitulo.text = "Historial (${registros.size})"

                lstRegistros.adapter =
                    ArrayAdapter<Registro>(this, android.R.layout.simple_list_item_1, registros)

            } else {
                noHayRegistros()
            }

        } else {
            noHayRegistros()
        }

        eventos()
    }

    private fun noHayRegistros() {
        lblTitulo.visibility = View.GONE
        lstRegistros.visibility = View.GONE
        pnlNoRegistros.visibility = View.VISIBLE
    }

    private fun eventos() {
        btnVolver.setOnClickListener(View.OnClickListener { this.finish() })
    }
}
