package br.unipar.trucoappkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VictoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML da segunda tela
        setContentView(R.layout.activity_victory)

        val txtVictoryMessage = findViewById<TextView>(R.id.txtVictoryMessage)
        val txtTime1 = findViewById<TextView>(R.id.txtTime1)
        val txtTime2 = findViewById<TextView>(R.id.txtTime2)
        val txtPontosTime1 = findViewById<TextView>(R.id.txtPontosTime1)
        val txtPontosTime2 = findViewById<TextView>(R.id.txtPontosTime2)
        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)

        val nomeTime1 = intent.getStringExtra("TIME_1")
        val nomeTime2 = intent.getStringExtra("TIME_2")
        txtTime1.text = nomeTime1
        txtTime2.text = nomeTime2


        val pontosTime1: Int = intent.getIntExtra("PONTOS_T1", 0)
        val pontosTime2: Int = intent.getIntExtra("PONTOS_T2", 0)

        txtPontosTime1.text = pontosTime1.toString()
        txtPontosTime2.text = pontosTime2.toString()

        var timeVencedor = ""
        timeVencedor = if (pontosTime1 > pontosTime2) {
            nomeTime1!!
        } else {
            nomeTime2!!
        }

        txtVictoryMessage.text = "Vencedores: ${timeVencedor}"

        btnPlayAgain.setOnClickListener{

            val resultIntent = Intent()
            resultIntent.putExtra("TIME_VENCEDOR", timeVencedor)
            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        }

    }
}
