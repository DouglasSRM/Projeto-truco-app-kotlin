package br.unipar.trucoappkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class GameAdapter(private val context: Context,
                   private val listaGames: MutableList<Game>)
    : ArrayAdapter<Game>(context, 0, listaGames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)

        val game = listaGames.get(position)

        val time1 = view.findViewById<TextView>(R.id.tvTime1)
        val time2 = view.findViewById<TextView>(R.id.tvTime2)

        time1.text = game.time1
        time2.text = game.time2

        return view
    }

}