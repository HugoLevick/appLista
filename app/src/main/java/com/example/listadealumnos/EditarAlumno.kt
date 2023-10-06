package com.example.listadealumnos

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditarAlumno: AppCompatActivity() {
    val dbAlumno = DBHelperAlumno(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.campos_alumno)

        val edtTxtNombre = findViewById<EditText>(R.id.editTextNombre)
        val edtTxtCuenta = findViewById<EditText>(R.id.editTextNumeroCuenta)
        val edtTxtCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val edtTxtImagen = findViewById<EditText>(R.id.editTextImagen)
        val btnEnviar = findViewById<Button>(R.id.btnAgregarAlumno)

        val idAlumno = intent.getIntExtra("id", -1)
        if(idAlumno == -1) {
            Toast.makeText(this, "No se pudo obtener información del alumno", Toast.LENGTH_LONG).show()
            finish()
        }
        edtTxtCuenta.setText(intent.getStringExtra("numCuenta"))
        edtTxtNombre.setText(intent.getStringExtra("nombre"))
        edtTxtCorreo.setText(intent.getStringExtra("email"))
        edtTxtImagen.setText(intent.getStringExtra("imagen"))

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

                val contentValues = ContentValues()
                contentValues.put("nombre", nombreRecibido)
                contentValues.put("numCuenta", cuentaRecibida)
                contentValues.put("email", correoRecibido)
                contentValues.put("imagen", imagenRecibida)

                val condiciones = "id = ?"
                val argumentos = arrayOf(idAlumno.toString())

                val numRowsUpdated = db.update("alumnos", contentValues, condiciones, argumentos)
                db.close()

                if(numRowsUpdated < 1) {
                    intent.putExtra("error", "No se pudo agregar el alumno")
                    setResult(Activity.RESULT_CANCELED, intent)

                } else {
                    intent.putExtra("mensaje", "Alumno agregado correctamente")
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
            }
        }
    }
}