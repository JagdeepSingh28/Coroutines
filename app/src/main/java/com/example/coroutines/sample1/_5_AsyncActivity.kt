package com.example.coroutines.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutines.R
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class _5_AsyncActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_5_async)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("debug", "Starting APIs")
            val time = measureTimeMillis {
                var answer1 : String? = null
                var answer2 : String? = null
                val job1 = launch { answer1 =  doNetworkCall1()}
                val job2 = launch { answer2 =  doNetworkCall2()}
                job1.join()
                job2.join()
                Log.d("debug", "The answer1 is $answer1")
                Log.d("debug", "The answer2 is $answer2")
            }
            Log.d("debug", "Total time to execute is $time")
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("debug", "Starting APIs")
            val time = measureTimeMillis {
                var answer1  = async { doNetworkCall1() }
                var answer2  = async { doNetworkCall2() }
                Log.d("debug", "The answer1 is ${answer1.await()}")
                Log.d("debug", "The answer2 is ${answer2.await()}")
            }
            Log.d("debug", "Total time to execute is $time")
        }
    }

    suspend fun doNetworkCall1():String{
        delay(3000L)
        return "Call1 Result"
    }

    suspend fun doNetworkCall2():String{
        delay(3000L)
        return "Call2 Result"
    }
}