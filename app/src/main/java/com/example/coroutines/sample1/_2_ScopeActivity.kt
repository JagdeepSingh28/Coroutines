package com.example.coroutines.sample1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coroutines.databinding.ActivityMainBinding
import com.example.coroutines.databinding.ActivityScopeBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
class _2_ScopeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScopeBinding

//    private lateinit var job: Job
//
//    val coroutineContext: CoroutineContext
//        get() = job + Dispatchers.Default + CoroutineName("Activity Scope") + CoroutineExceptionHandler { coroutineContext, throwable ->
//            println("Exception $throwable in context:$coroutineContext")
//        }

    val viewModel: _2_ScopeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            GlobalScope.launch {
                while (true) {
                    delay(1000)
                    Log.e("debug", "Still Running")
                }
            }
            GlobalScope.launch {
                delay(1000)
                Intent(this@_2_ScopeActivity, ResultActivity::class.java).also{
                    startActivity(it)
                    finish()
                }
            }
        }

        binding.button2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                while (true) {
                    delay(1000)
//                    Log.e("debug", "Still Running")
                    Log.e("debug", Thread.currentThread().name)
                }
            }
//            GlobalScope.launch {
//                delay(5000)
//                Intent(this@_2_ScopeActivity, ResultActivity::class.java).also{
//                    startActivity(it)
//                    finish()
//                }
//            }
        }

//        MainScope().launch {
//
//        }

        viewModel.result.observe(this,{
            Log.e("debug", "the result is $it" )
        })

    }
}