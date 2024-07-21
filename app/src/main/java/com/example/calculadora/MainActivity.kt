package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var Formula: TextView
    private lateinit var Resultado: TextView
    private lateinit var Input: EditText

    private var numeroAtual = StringBuilder()
    private var operacaoAtual: Char? = null
    private var ultimaOperacao: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Formula = findViewById(R.id.Formula)
        Resultado = findViewById(R.id.Resultado)
        Input = findViewById(R.id.Input)

        val buttons = mapOf(
            R.id.Zero to "0", R.id.Um to "1", R.id.Dois to "2", R.id.Tres to "3",
            R.id.Quatro to "4", R.id.Cinco to "5", R.id.Seis to "6",
            R.id.Sete to "7", R.id.Oito to "8", R.id.Nove to "9"
        )

        buttons.forEach { (buttonId, buttonText) ->
            findViewById<Button>(buttonId).setOnClickListener {
                appendNumber(buttonText)
            }
        }

        findViewById<Button>(R.id.Virgula).setOnClickListener {
            appendDecimal()
        }

        findViewById<Button>(R.id.Limpar).setOnClickListener {
            clear()
        }

        findViewById<Button>(R.id.Soma).setOnClickListener {
            setOperation('+')
        }

        findViewById<Button>(R.id.Subtracao).setOnClickListener {
            setOperation('-')
        }

        findViewById<Button>(R.id.Multiplicacao).setOnClickListener {
            setOperation('*')
        }

        findViewById<Button>(R.id.Divisao).setOnClickListener {
            setOperation('/')
        }

        findViewById<Button>(R.id.Radiciacao).setOnClickListener {
            performOperation('√')
        }

        findViewById<Button>(R.id.Potencia).setOnClickListener {
            setOperation('^')
        }

        findViewById<Button>(R.id.Igual).setOnClickListener {
            calculateResult()
        }

        // Adicionando um listener de texto para monitorar alterações no EditText
        Input.addTextChangedListener {
            numeroAtual.clear()
            numeroAtual.append(it.toString())
        }
    }

    private fun appendNumber(number: String) {
        numeroAtual.append(number)
        Input.setText(numeroAtual.toString())
    }

    private fun appendDecimal() {
        if (!numeroAtual.contains('.')) {
            if (numeroAtual.isEmpty()) {
                numeroAtual.append("0.")
            } else {
                numeroAtual.append('.')
            }
            Input.setText(numeroAtual.toString())
        }
    }

    private fun setOperation(operation: Char) {
        operacaoAtual = operation
        ultimaOperacao = numeroAtual.toString().toDoubleOrNull()
        numeroAtual.clear()
        Input.text.clear()
    }

    private fun clear() {
        numeroAtual.clear()
        operacaoAtual = null
        ultimaOperacao = null
        Formula.text = ""
        Resultado.text = "0"
        Input.setText("")
    }

    private fun performOperation(operation: Char) {
        when (operation) {
            '√' -> {
                if (numeroAtual.isNotEmpty()) {
                    val operand = numeroAtual.toString().toDoubleOrNull() ?: return
                    val result = Math.sqrt(operand)
                    Resultado.text = result.toString()
                    numeroAtual.clear()
                    numeroAtual.append(result.toString())
                    operacaoAtual = null
                    ultimaOperacao = null
                }
            }
        }
    }


    private fun calculateResult() {
        if (operacaoAtual != null && ultimaOperacao != null && numeroAtual.isNotEmpty()) {
            val current = numeroAtual.toString().toDoubleOrNull() ?: return
            val result = when (operacaoAtual) {
                '+' -> ultimaOperacao!! + current
                '-' -> ultimaOperacao!! - current
                '*' -> ultimaOperacao!! * current
                '/' -> ultimaOperacao!! / current
                '^' -> Math.pow(ultimaOperacao!!, current)
                else -> return
            }
            Resultado.text = result.toString()
            numeroAtual.clear()
            numeroAtual.append(result.toString())
            operacaoAtual = null
            ultimaOperacao = null
        }
    }
}

