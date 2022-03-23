package com.example.pradeo

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class DataApplication :Parcelable{
     var Nom : String =""
     var score : Int = 0
     var total : Int = 0
     var etat : String =""
    var  antivirus : ArrayList<DataAntiVirus> = ArrayList<DataAntiVirus>()
    constructor(Nom: String, score: Int, total : Int, etat : String, antiviri:ArrayList<DataAntiVirus>  ) {
        this.Nom = Nom
        this.score = score
        this.total = total
        this.etat = etat
        this.antivirus = antiviri
    }


    constructor(jsonObject : JSONObject , Nom: String) {
        if (jsonObject.has("data")) {
            var data = jsonObject.getJSONObject("data")
            var attribut = data.getJSONObject("attributes")
            var valeur = attribut.getJSONObject("last_analysis_stats")
            var total = valeur.getInt("harmless")+valeur.getInt("suspicious") + valeur.getInt("malicious")+valeur.getInt("undetected")
            var score = valeur.getInt("malicious")+valeur.getInt("suspicious")
            var etat : String = ""
            if(score ==0) {
                etat = "inconnu"
            }
            else if(valeur.getInt("malicious") >0) {
                etat = "Menace déctecté"
            }
            else if(valeur.getInt("suspicious") >0) {
                etat = "Suspition de menace"
            }
            else {
                etat = "CLEAN"
            }
            var anti : ArrayList<DataAntiVirus> = ArrayList<DataAntiVirus>()
            var t = attribut.getJSONObject("last_analysis_results")
            t.keys().forEach {
                anti.add(DataAntiVirus(t.getJSONObject(it).getString("engine_name") ,t.getJSONObject(it).getString("category"),t.getJSONObject(it).getString("result") ))
            }
            this.Nom = Nom
            this.score = score
            this.total = total
            this.etat = etat
            this.antivirus = anti
        }



    }





    constructor(parcel: Parcel) {
        Nom = parcel.readString()?:""
        score = parcel.readInt()?:0
        total = parcel.readInt()?:0
        etat = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nom)
        parcel.writeInt(score)
        parcel.writeInt(total)
        parcel.writeString(etat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataApplication> {
        override fun createFromParcel(parcel: Parcel): DataApplication {
            return DataApplication(parcel)
        }

        override fun newArray(size: Int): Array<DataApplication?> {
            return arrayOfNulls(size)
        }
    }


}