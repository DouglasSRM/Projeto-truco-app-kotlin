package br.unipar.trucoappkotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GamesActivity : AppCompatActivity() {

    private val listaGames = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_games)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        val lvGames = findViewById<ListView>(R.id.lvGames)
        val time1 = intent.getStringExtra("TIME_1") ?: ""
        val time2 = intent.getStringExtra("TIME_2") ?: ""
        val ptTime1 = intent.getIntExtra("VIT_T1", 0)
        val ptTime2 = intent.getIntExtra("VIT_T2", 0)

        val adapter = GameAdapter(this, listaGames)
        lvGames.adapter = adapter

        if (savedInstanceState != null) {
            val savedList = savedInstanceState.getParcelableArrayList<Game>("itemList")
            Log.d("GamesActivity", "Restaurando lista de jogos: $savedList")
            savedList?.let {
                listaGames.clear()
                listaGames.addAll(it)
                adapter.notifyDataSetChanged()
                Log.d("GamesActivity", "Lista de jogos após restauração: $listaGames")
            }
        }



        fun voltar(){

            finish()
        }

        btnSalvar.setOnClickListener{
            val savedGame = Game(time1, time2, ptTime1, ptTime2)
            listaGames.add(savedGame)
            adapter.notifyDataSetChanged()
        }

        btnVoltar.setOnClickListener {

            voltar()

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("GamesActivity", "Salvando lista de jogos: $listaGames")
        outState.putParcelableArrayList("itemList", ArrayList(listaGames))
    }

    override fun onStart() {
        super.onStart()
        Log.d("GamesActivity", "onStart chamado")
    }

    override fun onResume() {
        super.onResume()
        Log.d("GamesActivity", "onResume chamado")
    }

    override fun onPause() {
        super.onPause()
        Log.d("GamesActivity", "onPause chamado")
    }

    override fun onStop() {
        super.onStop()
        Log.d("GamesActivity", "onStop chamado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("GamesActivity", "onDestroy chamado")
    }

}
