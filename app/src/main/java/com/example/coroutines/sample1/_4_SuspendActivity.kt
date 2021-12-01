package com.example.coroutines.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutines.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class _4_SuspendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4_suspend)

        CoroutineScope(Dispatchers.IO).launch{
            Log.d("debug", "calling APIs")
            val time = measureTimeMillis {
                val result1 = doNetworkCall1()
                val result2 = doNetworkCall2(result1)
                Log.d("debug", "The final Result is $result2")
            }
            Log.d("debug", "Total time to execute is $time")
        }
    }

    suspend fun doNetworkCall1():String{
        delay(3000L)
        return "Call1 Result"
    }

    suspend fun doNetworkCall2(result1:String):String{
        delay(3000L)
        return "$result1 -+- Call2 Result"
    }
}