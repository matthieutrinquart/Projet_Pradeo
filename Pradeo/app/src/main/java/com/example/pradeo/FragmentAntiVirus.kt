package com.example.pradeo

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment


class FragmentAntiVirus : Fragment() {
    var listView: ListView? = null
    var retour: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_antivirus, container, false)
        var f : DataApplication = this.arguments?.getParcelable<Parcelable>("DATA") as DataApplication
        listView = view.findViewById(R.id.listantivirus) as ListView
        retour = view.findViewById(R.id.button2) as Button
        val adapter = ListAdaptateurAntiVirus(view.context, f.antivirus)
        listView!!.adapter = adapter
        retour!!.setOnClickListener { view ->
            changementFragment()
        }

        return view
    }

    fun changementFragment() {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        val fragment = FragmentMenu()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

}