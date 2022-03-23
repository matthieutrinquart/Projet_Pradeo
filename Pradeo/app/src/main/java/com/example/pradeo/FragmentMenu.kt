package com.example.pradeo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest


class FragmentMenu : Fragment() {
    var listView: ListView? = null
    var buttonscan: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        val view : View = inflater.inflate(R.layout.fragment_menu, container, false)
         listView = view.findViewById(R.id.listvirus) as ListView
         buttonscan = view.findViewById(R.id.IDscan) as Button


        buttonscan!!.setOnClickListener { view ->
            getallapps(view)
        }



        val file_name = view.context.filesDir.toString() + "/" + "donner.json"
        val t = File(file_name)
        if (!t.exists()) {
            getallapps(view)
        }


        val rep :ArrayList<DataApplication> =lecture(view.context)
        val adapter = ListAdaptateurMenu(view.context, rep)
        listView!!.adapter = adapter
        listView!!.onItemClickListener = OnItemClickListener { arg0, arg1, position, arg3 ->
            val item: DataApplication = adapter.getItem(position) as DataApplication
            changementFragment(item)
        }


        return view
    }


    fun changementFragment(d :DataApplication) {
        val bundle = Bundle()
        bundle.putParcelable("DATA", d)
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        val fragment = FragmentAntiVirus()
        fragment.setArguments(bundle)
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

    fun getallapps(view : View) {
        var infos: List<ApplicationInfo>  = view.context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA)
        var result = ArrayList<DataApplication>()
       // var j : Int = 0
        for (s : ApplicationInfo in infos ){
            val r = hash(s.publicSourceDir)
            var json = Getrequete(r)
            val jsonObject = JSONTokener(json).nextValue() as JSONObject
            result.add(DataApplication(jsonObject,view.context.packageManager.getApplicationLabel(s).toString()))
/*
            if(j>5) {
                ecrire(view.context,result)
                return
            }
            ++j

 */


        }
        ecrire(view.context,result)
    }

    fun Getrequete(hash : String) : String{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.virustotal.com/api/v3/files/" + hash)
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("x-apikey", "246296a1552dbab551fbc3090f9a5b8a5c4979f5dddfbc545ade21122d2b4074")
            .build()
        val reponse: Response = client.newCall(request).execute();
        return reponse.body()?.string()!!
    }

    fun hash(path : String) : String{
        val digest = MessageDigest.getInstance("SHA-256")
        Files.newInputStream(Paths.get(path)).use { input ->
            val buf = ByteArray(8192)
            var len: Int
            while (input.read(buf).also { len = it } > 0) {
                digest.update(buf, 0, len)
            }
        }
        val hash : ByteArray= digest.digest()
        return hash.toHex()
    }
    fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    fun ecrire(context: Context , json : ArrayList<DataApplication>) {
        try {
            val fOut = context.openFileOutput("donner.json", 0)
            val gson = Gson()
            val json : String = gson.toJson(json)
            fOut.write(json.toByteArray())
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun lecture(context: Context):ArrayList<DataApplication> {
        val input: InputStream = context.openFileInput("donner.json")
        val buffer = ByteArray(input.available())
        input.read(buffer)
        input.close()
        val text = String(buffer)
        val gson = Gson()
        val itemType = object : TypeToken<List<DataApplication>>() {}.type
        return gson.fromJson(text,itemType)
    }
}

