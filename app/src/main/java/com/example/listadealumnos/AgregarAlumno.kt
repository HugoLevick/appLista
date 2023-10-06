package com.example.listadealumnos

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarAlumno: AppCompatActivity() {
    val dbAlumno = DBHelperAlumno(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        val edtTxtNombre = findViewById<EditText>(R.id.editTextNombre)
        val edtTxtCuenta = findViewById<EditText>(R.id.editTextNumeroCuenta)
        val edtTxtCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val edtTxtImagen = findViewById<EditText>(R.id.editTextImagen)
        val btnEnviar = findViewById<Button>(R.id.btnAgregarAlumno)

        btnEnviar.setOnClickListener {
            if (edtTxtNombre.text.isEmpty() || edtTxtCuenta.text.isEmpty() || edtTxtCorreo.text.isEmpty()) {
                Toast.makeText(this, "Por favor, llena los campos", Toast.LENGTH_LONG).show()
            } else {
                val db = dbAlumno.writableDatabase
                val nombreRecibido = edtTxtNombre.text.toString()
                val cuentaRecibida = edtTxtCuenta.text.toString()
                val correoRecibido = edtTxtCorreo.text.toString()
                var imagenRecibida = edtTxtImagen.text.toString()

                if(imagenRecibida.isEmpty()) {
                    imagenRecibida = "https://api.multiavatar.com/${nombreRecibido}.png"
                }

                val newReg = ContentValues()
                newReg.put("nombre", nombreRecibido)
                newReg.put("numCuenta", cuentaRecibida)
                newReg.put("email", correoRecibido)
                newReg.put("imagen", imagenRecibida)

                val res = db.insert("alumnos", null, newReg)
                db.close()
                if(res.toInt() == -1) {
                    setResult(RESULT_CANCELED)
                    intent.putExtra("error", "No se pudo insertar el alumno")
                    finish()
                } else {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }

    }
}