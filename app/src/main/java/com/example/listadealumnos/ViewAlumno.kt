package com.example.listadealumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class ViewAlumno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_alumno)

        val imgAlumno = findViewById<ImageView>(R.id.imgVerAlumno)
        val txtNombre = findViewById<TextView>(R.id.txtVerNombre)
        val txtCuenta = findViewById<TextView>(R.id.txtVerCuenta)
        val txtCorreo = findViewById<TextView>(R.id.txtVerCorreo)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        val imagen = intent.getStringExtra("imagen")


        txtNombre.text = intent.getStringExtra("nombre")
        txtCuenta.text = intent.getStringExtra("cuenta")
        txtCorreo.text = intent.getStringExtra("correo")

        btnRegresar.setOnClickListener() {
            finish()
        }

        Picasso.get()
            .load(imagen)
            .into(imgAlumno)
    }
}
