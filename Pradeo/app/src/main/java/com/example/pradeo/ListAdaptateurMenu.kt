package com.example.pradeo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdaptateurMenu(private val context: Context, private val list :List<DataApplication> ): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return this.list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_adapteur_menu, parent, false)
        val Nom =  rowView.findViewById(R.id.IDNom) as TextView
        val mal = rowView.findViewById(R.id.IDmalicious) as TextView
        val result = rowView.findViewById(R.id.IDResultat) as TextView
        val nomtext = rowView.findViewById(R.id.Nom) as TextView
        val scoretext = rowView.findViewById(R.id.score) as TextView
        Nom.setText(list[position].Nom)
        mal.setText(list[position].score.toString() +"/" +list[position].total.toString())
        result.setText(list[position].etat)
        if(list[position].etat.equals("Inconnu")) {
            Nom.setTextColor(Color.GRAY)
            mal.setTextColor(Color.GRAY)
            result.setTextColor(Color.GRAY)
            nomtext.setTextColor(Color.GRAY)
            scoretext.setTextColor(Color.GRAY)
        }
        else if(list[position].etat.equals("Menace déctecté")) {
            Nom.setTextColor(Color.RED)
            mal.setTextColor(Color.RED)
            result.setTextColor(Color.RED)
            nomtext.setTextColor(Color.RED)
            scoretext.setTextColor(Color.RED)
        }
        else if(list[position].etat.equals("Suspition de menace")) {
            Nom.setTextColor(Color.rgb(255, 165, 0))
            mal.setTextColor(Color.rgb(255, 165, 0))
            result.setTextColor(Color.rgb(255, 165, 0))
            nomtext.setTextColor(Color.rgb(255, 165, 0))
            scoretext.setTextColor(Color.rgb(255, 165, 0))
        }
        else {
            result.setText("CLEAN")
            Nom.setTextColor(Color.GREEN);
            mal.setTextColor(Color.GREEN);
            result.setTextColor(Color.GREEN);
            nomtext.setTextColor(Color.GREEN)
            scoretext.setTextColor(Color.GREEN)
        }
        return rowView
    }
}