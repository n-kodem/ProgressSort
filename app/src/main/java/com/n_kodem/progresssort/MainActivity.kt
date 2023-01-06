package com.n_kodem.progresssort

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import java.util.Random
import kotlin.concurrent.timer
import kotlin.streams.toList

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.sortBtn).setOnClickListener {
            findViewById<ProgressBar>(R.id.progressBar).progress=0
            findViewById<LinearLayout>(R.id.unsorted).removeAllViews()
            findViewById<LinearLayout>(R.id.sorted).removeAllViews()

            val listLen = if (findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString()!="")
                findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString().toInt()
            else 10
            AsyncTask.execute {
                val currentTime = System.currentTimeMillis()
                val listToSort = Random().ints(listLen.toLong()).toList().toMutableList()
                for (element in listToSort){
                    val textToAdd = TextView(this.baseContext)
                    textToAdd.text="$element"
                    runOnUiThread { findViewById<LinearLayout>(R.id.unsorted).addView(textToAdd) }
                }
                val listSorted:List<Int> = sortWithProgress(listToSort, findViewById(R.id.progressBar))
                runOnUiThread { findViewById<TextView>(R.id.time).text="Time: ${"%.2f".format((System.currentTimeMillis()-currentTime).toDouble()/1000)} s" }
                for (element in listSorted) {
                    val textToAdd = TextView(baseContext)
                    textToAdd.text = "$element"
                    runOnUiThread { findViewById<LinearLayout>(R.id.sorted).addView(textToAdd) }

                }

            }
        }

    }
    private fun sortWithProgress(list: MutableList<Int>, progressBar: ProgressBar): List<Int> {
        val size = list.size
        for (i in 0 until size - 1) {
            for (j in 0 until size - i - 1) {
                if (list[j] > list[j + 1]) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
            progressBar.progress = ((i + 1).toDouble() / (size-1) * 100).toInt()
        }
        return list
    }
}