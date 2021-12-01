package com.example.coroutines.sample1.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutines.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class _8_FlowActivity : AppCompatActivity() {

    val list = listOf(1,2,3,4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8_flow)

        // Example 1
        val flow = flow {
            for (i in 1..10){
                emit(i)
                delay(1000)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            flow.collect {
                Log.d("debug", "$it")
            }
        }


        // Example 2 () fixed no.
        val flow2 = flowOf("A", "B", "C").map {
            compute(it)
        }

        // FLow from collection
        val flow2_1 = list.asFlow().onEach {
            delay(1000)
        }

        // By Default producer works on the same context as of consumer context
        runBlocking {
            flow2.collect {
                println(it)
            }
        }

        // Example 3
        val flow3 = flowOf("A", "B", "C")
            .map { onA()}   // on IO dispatcher
            .flowOn(Dispatchers.IO)
            .map { onB() }  // on collector Context(Main)

        CoroutineScope(Dispatchers.Main).launch {
            flow3.collect {
                Log.d("debug", "$it")
            }
        }

    }

    suspend fun compute(string: String):String{
        delay(1000)
        return string
    }

    suspend fun onA(){
        delay(1000)
        Log.d("debug", "onA: ${Thread.currentThread().name}")
    }

    suspend fun onB(){
        delay(1000)
        Log.d("debug", "onA: ${Thread.currentThread().name}")
    }
}