package com.example.listadealumnos

import Alumno
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
    val txtCuenta: TextView = itemView.findViewById(R.id.txtCuenta)
    val imgAlumno: ImageView = itemView.findViewById(R.id.imgVerAlumno)
    val containerOpciones: TextView = itemView.findViewById(R.id.textViewOptions)
    val containerAlumno: LinearLayout = itemView.findViewById(R.id.alumnoLayout)
}
class AlumnoAdapter(private val data: MutableList<Alumno>, private val dbAlumno: DBHelperAlumno) : RecyclerView.Adapter<CustomViewHolder>() {
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
            showOptionsMenu(holder.containerOpciones, item.id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun showOptionsMenu(view: View, idAlumno: Int) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_main, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.borrar -> {
                    val db = dbAlumno.writableDatabase
                    val condicion = "id = ?"
                    val argumentos = arrayOf(idAlumno.toString())
                    db.delete("alumnos", condicion, argumentos)
                    db.close()

                    for(i in data.indices) {
                        val alumno = data[i]
                        if (alumno.id == idAlumno) {
                            data.remove(alumno)
                            this.notifyItemRemoved(i)
                            break
                        }
                    }
                    true
                }
                R.id.editar -> {
                    // Handle the "Delete" action here
                    TODO();
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}