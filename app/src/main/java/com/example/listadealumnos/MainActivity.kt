package com.example.listadealumnos

import Alumno
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val alumnos = mutableListOf<Alumno>()
    private val dbAlumno = DBHelperAlumno(this)
    private val adapter = AlumnoAdapter(alumnos, dbAlumno)

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Alumno agregado correctamente", Toast.LENGTH_SHORT).show()
                obtenerAlumnos()
            } else if (result.resultCode == Activity.RESULT_CANCELED){
                val data: Intent? = result.data
                val error = data?.getStringExtra("error")
                if(error != null) {
                    Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        obtenerAlumnos()

        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, AgregarAlumno::class.java)
            resultLauncher.launch(intent)

        }
        recyclerView.adapter = adapter

    }

    private fun obtenerAlumnos() {
        val db = dbAlumno.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM alumnos", null)
        alumnos.clear()
        if (cursor.moveToFirst()) {
            do {
                val idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombreAlumno = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val numCuentaAlumno = cursor.getString(cursor.getColumnIndexOrThrow("numCuenta"))
                val emailAlumno = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val imagenAlumno = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

                alumnos.add(Alumno(idAlumno, nombreAlumno, numCuentaAlumno, emailAlumno, imagenAlumno))
            } while (cursor.moveToNext());
        }
        cursor.close()
        db.close()
        this.adapter.notifyDataSetChanged()
    }
}