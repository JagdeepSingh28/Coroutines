package com.example.coroutines.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutines.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.util.concurrent.Executors

class _3_DispacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3_dispacter)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("debug", "IO - "+Thread.currentThread().name)
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("debug", "Main - "+Thread.currentThread().name)
        }

        CoroutineScope(Dispatchers.Default).launch {
            Log.d("debug", "Default - "+Thread.currentThread().name)
        }

        CoroutineScope(Dispatchers.Unconfined).launch {
            Log.d("debug", "Unconfined - "+Thread.currentThread().name)
        }

        CoroutineScope(newSingleThreadContext("MyThread")).launch {
            Log.d("debug", "MyThread - "+Thread.currentThread().name)
        }
    }
}