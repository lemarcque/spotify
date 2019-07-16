package io.capsulo.spotify.features.player

import android.content.res.AssetManager
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Class that load content of lyrics in text files.
 */
class LyricsReader(val listener: LyricsReaderLister) {

    private var reader: BufferedReader? = null


    interface LyricsReaderLister {
        fun onLoaded(list: List<Referent>)
    }

    fun load(assets: AssetManager, path: String) {
        try {
            reader = BufferedReader(InputStreamReader(assets.open(path)))

            val referentList = mutableListOf<Referent>()
            val data = reader!!.readLines().toString()
            val array = JSONArray(data)[0] as JSONArray

            for(i in 0 until array.length()) {

                val obj = array.get(i) as JSONObject
                val keys = obj.names()

                //
                var tag = ""
                var text = ""
                var time: Double? = null

                for(j in 0 until keys.length()) {
                    val key = keys.getString(j)
                    val value = obj[keys.getString(j)]

                    if(key != "time") {
                        tag = key
                        text = value as String
                    }else {
                        time = value.toString().toDouble()
                    }
                }

                referentList.add(Referent(tag, text, time))
            }

            // fire event
            // TODO : reader!!.readLines().joinToString("\n")
            listener.onLoaded(referentList)

        }catch (e: IOException) {
            // ...
        }
        finally {
            if(reader != null) {
                try {
                    reader?.close()
                }catch (e: IOException) {
                    // ...
                }
            }
        }
    }
}