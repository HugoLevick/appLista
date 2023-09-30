package com.example.listadealumnos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

val alumnos = mutableListOf<Alumno>()
val adapter = CustomAdapter(alumnos)

class MainActivity : AppCompatActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val nombreRecibido = data?.getStringExtra("nombre") ?: "Error"
            val cuentaRecibida = data?.getStringExtra("cuenta") ?: "Error"
            val correoRecibido = data?.getStringExtra("correo") ?: "Error"

            alumnos.add(Alumno(nombreRecibido, cuentaRecibida, correoRecibido))
            Log.i("alumno", "${alumnos.last().nombre}")
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        alumnos.add(Alumno("Juan Pérez", "12345", "juan.perez@ucol.mx"))
        alumnos.add(Alumno("María González", "67890", "maria.gonzalez@ucol.mx"))
        alumnos.add(Alumno("Carlos Rodríguez", "54321", "carlos.rodriguez@ucol.mx"))
        alumnos.add(Alumno("Laura Martínez", "98765", "laura.martinez@ucol.mx"))
        alumnos.add(Alumno("Pedro Sánchez", "11111", "pedro.sanchez@ucol.mx"))
        alumnos.add(Alumno("Sofía López", "22222", "sofia.lopez@ucol.mx"))


        super.onCreate(savedInstanceState)
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
}

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
    val txtCuenta: TextView = itemView.findViewById(R.id.txtCuenta)
    val imgAlumno: ImageView = itemView.findViewById(R.id.imgVerAlumno)
    val containerOpciones: TextView = itemView.findViewById(R.id.textViewOptions)
    val containerAlumno: LinearLayout = itemView.findViewById(R.id.alumnoLayout)
}

class CustomAdapter(private val data: MutableList<Alumno>) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.alumno_layout, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        holder.txtNombre.text = item.nombre
        holder.txtCuenta.text = item.cuenta
        Picasso.get()
            .load(item.imagen)
            .into(holder.imgAlumno)
        holder.containerAlumno.setOnClickListener(){
            val intent = Intent(holder.itemView.context, ViewAlumno::class.java)

            // Add any data you want to pass to the ViewAlumno activity (optional)
            intent.putExtra("nombre", item.nombre)
            intent.putExtra("cuenta", item.cuenta)
            intent.putExtra("correo", item.correo)
            intent.putExtra("imagen", item.imagen)

            // Start the activity
            holder.itemView.context.startActivity(intent)
        }

        holder.containerOpciones.setOnClickListener {
            showOptionsMenu(holder.containerOpciones, item.cuenta)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun showOptionsMenu(view: View, cuenta: String) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_main, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.borrar -> {
                    for(i in alumnos.indices) {
                        val alumno = data[i]
                        Log.i("Alum Cuenta","${alumno.cuenta}")
                        Log.i("Num Cuenta","${cuenta}")
                        if (alumno.cuenta == cuenta) {
                            Log.i("Num Cuenta I","${i}")
                            alumnos.remove(alumno)
                            this.notifyItemRemoved(i)
                            break
                        }
                    }
                    true
                }
                R.id.editar -> {
                    // Handle the "Delete" action here
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}

class Alumno(val nombre: String, val cuenta: String, val correo: String) {
    val imagen: String = "https://api.multiavatar.com/${nombre}.png"
}