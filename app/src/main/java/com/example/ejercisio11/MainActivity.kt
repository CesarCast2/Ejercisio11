package com.example.ejercisio11

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercisio11.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var contactos : ArrayList<Contacto>
    lateinit var binding: ActivityMainBinding // -- B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater) // --B
        setContentView(binding.root) // -- B
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onResume(){
        super.onResume()
        val db = openOrCreateDatabase("miBD", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS " + "Contactos(id INTEGER PRiMARY KEY AUTOINCREMENT," +
        "nombre TEXT NOT NULL, telefono TEXT NOT NULL);")

        val cursor = db.rawQuery("SELECT * FROM Contactos", null)
        contactos = ArrayList<Contacto>()

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val telefono = cursor.getString(2)
            val contacto = Contacto(nombre, telefono)
            contactos.add(contacto)
        }
        val gson = Gson()
        val contenido = gson.toJson(contactos)
        Log.v("pruebas", contenido)

        db.close()
    }
    fun guardar(v:View){
        Log.v("Pruebas", "Se presiono")
        val nombre = binding.edNombre.text.toString()
        val telefono = binding.edTelefono.text.toString()
        val db = openOrCreateDatabase("miBD", MODE_PRIVATE, null)
        val parametros = ContentValues()
        parametros.put("nombre", nombre)
        parametros.put("telefono", nombre)
        db.insert("Contactos", null, parametros)
        db.close()
        Toast.makeText(this, "Se grabo correctamente", Toast.LENGTH_LONG).show()
        binding.edNombre.setText("")
        binding.edTelefono.setText("")
    }
}