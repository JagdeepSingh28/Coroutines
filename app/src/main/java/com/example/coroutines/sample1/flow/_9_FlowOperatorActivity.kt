package com.example.coroutines.sample1.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutines.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class _9_FlowOperatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8_flow)

        mapExample()
        filterExample()
        takeExmaple()
        takeWhileExample()
        zipExample()
    }

    private fun mapExample() {
        runBlocking {
            (1..3).asFlow()
                .map { convertNumToString(it) }  // con also call a suspend function
                .collect { Log.e("debug", it ) }
        }
    }

    private fun convertNumToString(num: Int):String{
        return "$num"
    }

    private fun filterExample(){
        runBlocking {
            (1..10).asFlow()
                .filter { filterOdd(it) }
                .collect { Log.e("debug", "$it" ) }
        }
    }

    private fun filterOdd(num: Int):Boolean{
        return (num % 2 == 0).not()
    }

    private fun takeExmaple(){
        runBlocking {
            (1..4).asFlow()
                .take(4)
                .collect { Log.e("debug", "$it" ) }
        }
    }

    private fun takeWhileExample(){
        val startTime = System.currentTimeMillis()
        runBlocking {
            (1..1000).asFlow()
                .takeWhile { System.currentTimeMillis() - startTime <10 }
                .collect { Log.e("debug", "$it" ) }
        }
    }

    private fun zipExample(){
        val num = (1..3).asFlow()
        val str = flowOf("one", "two", "three")
        runBlocking {
            num.zip(str){
                a,b -> "$a and $b"
            }.collect { Log.e("debug", it ) }
        }
    }

}