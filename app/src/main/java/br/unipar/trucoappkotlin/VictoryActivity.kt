package br.unipar.trucoappkotlin

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
        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)

        btnPlayAgain.setOnClickListener{

            finish()
        }



    }
}
