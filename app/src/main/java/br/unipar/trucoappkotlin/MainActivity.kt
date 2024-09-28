package br.unipar.trucoappkotlin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTime1 = findViewById<TextView>(R.id.txtTime1)
        val txtTime2 = findViewById<TextView>(R.id.txtTime2)
        val txtPontosTime1 = findViewById<TextView>(R.id.txtPontosTime1)
        val txtPontosTime2 = findViewById<TextView>(R.id.txtPontosTime2)
        val btnMenosPtTime1 = findViewById<Button>(R.id.btnMenosPtTime1)
        val btnMenosPtTime2 = findViewById<Button>(R.id.btnMenosPtTime2)
        val btnMaisPtTime1 = findViewById<Button>(R.id.btnMaisPtTime1)
        val btnMaisPtTime2 = findViewById<Button>(R.id.btnMaisPtTime2)
        val btnTruco = findViewById<Button>(R.id.btnTruco)
        val txtVitorias = findViewById<TextView>(R.id.txtVitorias)
        val txtVitTime1 = findViewById<TextView>(R.id.txtVitTime1)
        val txtVitTime2 = findViewById<TextView>(R.id.txtVitTime2)
        var pontosTime1: Int = 0
        var pontosTime2: Int = 0
        var pontosAdd: Int = 1

        fun updatePtTime1(){
            txtPontosTime1.text = pontosTime1.toString()
        }

        fun updatePtTime2(){
            txtPontosTime2.text = pontosTime2.toString()
        }

        fun resetBtnTruco() {
            pontosAdd = 1
            btnTruco.text = "Truco!"
        }

        btnMaisPtTime1.setOnClickListener {
            when (pontosAdd) {
                1, 3, 6, 9, 12 -> {
                    pontosTime1 += pontosAdd
                    updatePtTime1()
                    resetBtnTruco()
                } else -> Toast.makeText(this, "Erro #1", Toast.LENGTH_SHORT).show()
                }
            if (pontosTime1 >= 12) {
                pontosTime1 = 12
                updatePtTime1()
                fimDaRodada()
            }
        }

        btnMaisPtTime2.setOnClickListener {
            when (pontosAdd) {
                1, 3, 6, 9, 12 -> {
                    pontosTime2 += pontosAdd
                    updatePtTime2()
                    resetBtnTruco()
                } else -> Toast.makeText(this, "Erro #1", Toast.LENGTH_SHORT).show()
            }
            if (pontosTime2 >= 12) {
                pontosTime2 = 12
                updatePtTime2()
                fimDaRodada()
            }
        }

        btnMenosPtTime1.setOnClickListener {
            if (pontosTime1 > 0) {
                pontosTime1 -= 1
                updatePtTime1()
            }
        }

        btnMenosPtTime2.setOnClickListener {
            if (pontosTime2 > 0) {
                pontosTime2 -= 1
                updatePtTime2()
            }
        }

        btnTruco.setOnClickListener {
            when (pontosAdd) {
                1 -> {
                    pontosAdd = 3
                    btnTruco.text = "Truco (6)"
                }
                3, 6 -> {
                    pontosAdd += 3
                    btnTruco.text = "Truco (" + (pontosAdd + 3) + ")"
                }
                9 -> {
                    pontosAdd += 3
                    btnTruco.text = "Truco(1)"
                }
                12 -> {
                    resetBtnTruco()
                }
                else -> Toast.makeText(this, "Erro #1", Toast.LENGTH_SHORT).show()
            }

        }

        fun mudarNomeTime(time: Int) {
            val editText = EditText(this)

            val txtTime = when (time) {
                1 -> txtTime1
                2 -> txtTime2
                else -> null
            }

            val dialogNome = AlertDialog.Builder(this)
                .setTitle("Nome atual: ${txtTime?.text}")
                .setMessage("Insira o novo nome abaixo: ")
                .setView(editText)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancelar", null)
                .create()

            dialogNome.show()

            val btnOK: Button = dialogNome.getButton(AlertDialog.BUTTON_POSITIVE)
            btnOK.isEnabled = false

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(txt: Editable?) {
                    // Apenas habilita o botão OK caso o EditText não esteja vazio
                    btnOK.isEnabled = (txt?.isNotEmpty() == true)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            btnOK.setOnClickListener {
                val novoNome = editText.text.toString()
                txtTime?.text = novoNome
                Toast.makeText(this, "Nome alterado para: $novoNome", Toast.LENGTH_SHORT).show()
                dialogNome.dismiss()
            }
        }

        txtTime1.setOnClickListener {
            mudarNomeTime(1)
        }

        txtTime2.setOnClickListener {
            mudarNomeTime(2)
        }

    }

    private fun fimDaRodada() { //Para fazer...
        val intent = Intent(this, VictoryActivity::class.java)
        startActivity(intent)
    }

}

/**
 *  # Erros #
 *  Erro #1 = Variável pontosAdd com valor diferente de 1, 3, 6, 9 ou 12;
 *
 */