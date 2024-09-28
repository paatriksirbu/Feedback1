package com.example.feedback1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import android.widget.PopupWindow
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.text
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.feedback1.databinding.PopupAgregarNovelaBinding
import com.example.feedback1.databinding.PopupEliminarNovelaBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddBook: Button
    private lateinit var listViewNovels: ListView
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var novelViewModel: NovelViewModel
    private val novelas: MutableList<Novel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewNovels = findViewById(R.id.listViewNovels)
        novelAdapter = NovelAdapter(novelas)
        listViewNovels.adapter = novelAdapter


        val buttonAgregarNovela = findViewById<Button>(R.id.buttonAgregarNovela)
        val buttonEliminarNovela = findViewById<Button>(R.id.buttonEliminarNovela)
        val buttonReviews = findViewById<Button>(R.id.buttonReviews)
        val buttonVerNovelas = findViewById<Button>(R.id.buttonVerNovelas)


       buttonAgregarNovela.setOnClickListener {
           mostrarPopupAgregarNovela()
       }

        buttonEliminarNovela.setOnClickListener{
            mostrarPopupEliminarNovela()
        }

    }

    private fun mostrarPopupAgregarNovela() {
        val binding = PopupAgregarNovelaBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this).apply {
            setView(binding.root)
            setTitle("Agregar Novela")
            setPositiveButton("Agregar") { dialog, _ ->
                val titulo = binding.editTextTitulo.text.toString()
                val autor = binding.editTextAutor.text.toString()
                val year = binding.editTextYear.text.toString()

                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                if (titulo.isNotEmpty() && autor.isNotEmpty() && year.isNotEmpty()) {

                    if (year.toInt() > currentYear) {
                        Toast.makeText(this@MainActivity, "El año de publicación no puede ser mayor al año actual", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val nuevaNovela = Novel(titulo, autor, year.toInt())
                    novelas.add(nuevaNovela)
                    novelAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        }.create()
        dialog.show()
    }

    private fun mostrarPopupEliminarNovela() {
        val binding = PopupEliminarNovelaBinding.inflate(layoutInflater)

        //Verificamos si hay novelas en la lista para poder eliminar
        if (novelas.isEmpty()) {
            Toast.makeText(this, "No hay novelas para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val listaTitulos = novelas.map { "${it.titulo} (${it.year})" }.toTypedArray()


        val dialog = AlertDialog.Builder(this).apply {
            setView(binding.root)
            setTitle("Eliminar Novela")

            // Configurar el Spinner con los títulos de las novelas
            binding.spinnerNovelas.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, listaTitulos)

            setPositiveButton("Eliminar") { dialog, _ ->
                val selectedIndex = binding.spinnerNovelas.selectedItemPosition

                if (selectedIndex >= 0) {
                    // Eliminar la novela seleccionada
                    val novelaSeleccionada = novelas[selectedIndex]
                    novelas.removeAt(selectedIndex)
                    novelAdapter.notifyDataSetChanged()

                    Toast.makeText(this@MainActivity, "Novela '${novelaSeleccionada.titulo}' eliminada", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }

            setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        }.create()
        dialog.show()
    }
}
