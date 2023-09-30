package com.example.listadealumnos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AgregarAlumno: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        val edtTxtNombre = findViewById<EditText>(R.id.editTextNombre)
        val edtTxtCuenta = findViewById<EditText>(R.id.editTextNumeroCuenta)
        val edtTxtCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val btnEnviar = findViewById<Button>(R.id.btnAgregarAlumno)

        btnEnviar.setOnClickListener {
            if (edtTxtNombre.text.isNotEmpty() && edtTxtCuenta.text.isNotEmpty() && edtTxtCorreo.text.isNotEmpty()) {
                val datos = Intent()
                datos.putExtra("nombre", edtTxtNombre.text.toString())
                datos.putExtra("cuenta", edtTxtCuenta.text.toString())
                datos.putExtra("correo", edtTxtCorreo.text.toString())
                setResult(Activity.RESULT_OK, datos)
                finish()
            }
        }

    }
}