package com.example.pradeo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdaptateurAntiVirus(private val context: Context, private val list :List<DataAntiVirus> ): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return this.list.size
    }

    override fun getItem(position: Int): DataAntiVirus {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_adaptateur_antivirus, parent, false)
        val Nom =  rowView.findViewById(R.id.NomAnti) as TextView
        val mal = rowView.findViewById(R.id.resultAnti) as TextView
        Nom.setText(getItem(position).Nom)
        println(getItem(position).result)
        if(getItem(position).result.equals("null")) {
            mal.setText(getItem(position).category)
        }
        else {
            mal.setText(getItem(position).result)
        }
        if(getItem(position).category.equals("undetected")){
            Nom.setTextColor(Color.GREEN);
            mal.setTextColor(Color.GREEN);

        }
        else if(getItem(position).category.equals("type-unsupported")){
            Nom.setTextColor(Color.GRAY);
            mal.setTextColor(Color.GRAY);

        }
        else if(getItem(position).category.equals("malicious")){
            Nom.setTextColor(Color.RED);
            mal.setTextColor(Color.RED);

        }
        else if(getItem(position).category.equals("suspicious")){
            Nom.setTextColor(Color.rgb(255, 165, 0));
            mal.setTextColor(Color.rgb(255, 165, 0));

        }



        return rowView
    }
}