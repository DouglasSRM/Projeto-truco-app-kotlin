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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

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
        val btnResetarVitorias = findViewById<Button>(R.id.btnResetarVitorias)
        val btnTruco = findViewById<Button>(R.id.btnTruco)
        val txtVitTime1 = findViewById<TextView>(R.id.txtVitTime1)
        val txtVitTime2 = findViewById<TextView>(R.id.txtVitTime2)
        var pontosTime1: Int = 0
        var pontosTime2: Int = 0
        var pontosAdd: Int = 1
        var vitTime1: Int = 0
        var vitTime2: Int = 0

        fun updatePtTime1() {
            txtPontosTime1.text = pontosTime1.toString()
        }

        fun updatePtTime2() {
            txtPontosTime2.text = pontosTime2.toString()
        }

        fun salvarDados() {
            val sharedPreferences = getSharedPreferences("TrucoApp", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("NOME_TIME_1", txtTime1.text.toString())
            editor.putString("NOME_TIME_2", txtTime2.text.toString())
            editor.putInt("PONTOS_TIME_1", pontosTime1)
            editor.putInt("PONTOS_TIME_2", pontosTime2)
            editor.putInt("VIT_TIME_1", vitTime1)
            editor.putInt("VIT_TIME_2", vitTime2)
            editor.apply()
        }

        fun recuperarDados() {
            val sharedPreferences = getSharedPreferences("TrucoApp", MODE_PRIVATE)
            txtTime1.text = sharedPreferences.getString("NOME_TIME_1", "Nós")
            txtTime2.text = sharedPreferences.getString("NOME_TIME_2", "Eles")
            pontosTime1 = sharedPreferences.getInt("PONTOS_TIME_1", 0)
            pontosTime2 = sharedPreferences.getInt("PONTOS_TIME_2", 0)
            vitTime1 = sharedPreferences.getInt("VIT_TIME_1", 0)
            vitTime2 = sharedPreferences.getInt("VIT_TIME_2", 0)

            // Atualiza os TextViews com os valores recuperados
            updatePtTime1()
            updatePtTime2()
            txtVitTime1.text = vitTime1.toString()
            txtVitTime2.text = vitTime2.toString()
        }

        recuperarDados()

        fun resetBtnTruco() {
            pontosAdd = 1
            btnTruco.text = "Truco!"
        }

        fun checkBtnResetVit() {
            // Habilita o botão se pelo menos um dos contadores de vitória for diferente de zero
            btnResetarVitorias.isEnabled = vitTime1 != 0 || vitTime2 != 0
        }

        checkBtnResetVit()

        fun updateVitorias(vencedor: String?) {
            if (vencedor == txtTime1.text) {
                vitTime1++
                txtVitTime1.text = vitTime1.toString()
            }
            if (vencedor == txtTime2.text) {
                vitTime2++
                txtVitTime2.text = vitTime2.toString()
            }
            pontosTime1 = 0
            pontosTime2 = 0
            updatePtTime1()
            updatePtTime2()
            checkBtnResetVit()
            salvarDados()
        }

        // Bloco para pegar a informação de quem venceu a partida quando o app voltar da
        // Activity Victory para a Activity Main
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val timeVencedor = result.data?.getStringExtra("TIME_VENCEDOR")
                updateVitorias(timeVencedor)
            }
        }

        fun fimDaRodada() {
            val intent = Intent(this, VictoryActivity::class.java)
            intent.putExtra("TIME_1", txtTime1.text)
            intent.putExtra("TIME_2", txtTime2.text)
            intent.putExtra("PONTOS_T1", pontosTime1)
            intent.putExtra("PONTOS_T2", pontosTime2)
            activityResultLauncher.launch(intent)
        }

        btnMaisPtTime1.setOnClickListener {
            when (pontosAdd) {
                1, 3, 6, 9, 12 -> {
                    pontosTime1 = (pontosTime1 + pontosAdd).coerceAtMost(12)
                    updatePtTime1()
                    resetBtnTruco()
                }
                else -> Toast.makeText(this, "Erro #1", Toast.LENGTH_SHORT).show()
            }
            if (pontosTime1 >= 12) {
                fimDaRodada()
            }
            salvarDados()
        }

        btnMaisPtTime2.setOnClickListener {
            when (pontosAdd) {
                1, 3, 6, 9, 12 -> {
                    pontosTime2 = (pontosTime2 + pontosAdd).coerceAtMost(12)
                    updatePtTime2()
                    resetBtnTruco()
                }
                else -> Toast.makeText(this, "Erro #1", Toast.LENGTH_SHORT).show()
            }
            if (pontosTime2 >= 12) {
                fimDaRodada()
            }
            salvarDados()
        }

        btnMenosPtTime1.setOnClickListener {
            if (pontosTime1 > 0) {
                pontosTime1 -= 1
                updatePtTime1()
            }
            salvarDados()
        }

        btnMenosPtTime2.setOnClickListener {
            if (pontosTime2 > 0) {
                pontosTime2 -= 1
                updatePtTime2()
            }
            salvarDados()
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

        btnResetarVitorias.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Resetar Vitórias")
                .setMessage("Deseja zerar o contador de vitórias?")
                .setPositiveButton("OK") { dialog, _ ->
                    vitTime1 = 0
                    vitTime2 = 0
                    txtVitTime1.text = "0"
                    txtVitTime2.text = "0"
                    Toast.makeText(this, "Contador de vitórias zerado!", Toast.LENGTH_SHORT).show()
                    checkBtnResetVit()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar", null)
                .create()

            dialog.show()
            salvarDados()
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

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
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
            salvarDados()
        }

        txtTime2.setOnClickListener {
            mudarNomeTime(2)
            salvarDados()
        }

    }


}

/**
 *  # Erros #
 *  Erro #1 = Variável pontosAdd com valor diferente de 1, 3, 6, 9 ou 12;
 *
 */