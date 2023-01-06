package com.n_kodem.progresssort

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import java.util.Random
import kotlin.streams.toList

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.sortBtn).setOnClickListener {
            findViewById<ProgressBar>(R.id.progressBar).progress=0
            val listLen = if (findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString()!="")
                findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString().toInt()
            else 10

            val listToSort = Random().ints(listLen.toLong()).toList().toMutableList()
            AsyncTask.execute {
                val sortedList = sortWithProgress(listToSort,findViewById(R.id.progressBar))
            }


        }

    }
    fun sortWithProgress(list: MutableList<Int>, progressBar: ProgressBar): List<Int> {
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
            println(progressBar.progress)
        }
        return list
    }
}