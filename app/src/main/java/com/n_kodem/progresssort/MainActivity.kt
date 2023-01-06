package com.n_kodem.progresssort

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import java.util.Random
import kotlin.streams.toList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.sortBtn).setOnClickListener {
            val listLen = if (findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString()!="")
                findViewById<EditText>(R.id.numberOfNumbersToSort).text.toString().toInt()
            else 10

            val listToSort = Random().ints(listLen.toLong()).toList().toMutableList()
            val sortedList = quickSort(listToSort,findViewById(R.id.progressBar))

        }

    }
    fun quickSort(list: List<Int>, progressBar: ProgressBar): List<Int> {
        if (list.size <= 1) return list

        val pivot = list[list.size / 2]
        val less = list.filter { it < pivot }
        val equal = list.filter { it == pivot }
        val greater = list.filter { it > pivot }

        val sortedLess = quickSort(less, progressBar)
        val sortedGreater = quickSort(greater, progressBar)

        progressBar.progress = (progressBar.progress + 1) % 100

        return sortedLess + equal + sortedGreater
    }
}