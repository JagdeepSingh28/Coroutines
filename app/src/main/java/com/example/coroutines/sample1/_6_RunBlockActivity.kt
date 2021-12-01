package com.example.coroutines.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutines.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class _6_RunBlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_6_run_block)

        GlobalScope.launch {
            delay(1000L)
        }

//        doSomeComputation()
        runBlocking {
            doSomeComputation()
        }
    }

    suspend fun doSomeComputation():String{
        delay(3000L)
        return "Call1 Result"
    }

}