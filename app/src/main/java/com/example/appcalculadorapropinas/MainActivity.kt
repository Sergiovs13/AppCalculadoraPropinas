package com.example.appcalculadorapropinas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.appcalculadorapropinas.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botonCalculo.setOnClickListener { calculo() }

        binding.costeServicioEt.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view,keyCode)}
    }

    private fun calculo() {
        val precioET = binding.costeServicioEt.text.toString()
        val precio = precioET.toDoubleOrNull()

        if (precio == null || precio == 0.0) {
            mostrarPropina(0.0)
            return
        }

        val porcentaje = when (binding.opciones.checkedRadioButtonId) {
            R.id.boton_20 -> 0.20
            R.id.boton_18 -> 0.18
            else -> 0.15
        }

        var propina = precio * porcentaje

        if (binding.redondeo.isChecked) propina = kotlin.math.ceil(propina)
        mostrarPropina(propina)

    }

    private fun mostrarPropina(propina: Double) {
        val propinaFormateada = NumberFormat.getCurrencyInstance().format(propina)
        binding.resultadoPropina.text = getString(R.string.cantidad, propinaFormateada)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return  true
        }
        return false
    }
}