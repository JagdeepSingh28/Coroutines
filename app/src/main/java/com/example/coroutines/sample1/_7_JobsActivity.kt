package com.example.coroutines.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutines.databinding.ActivityMainBinding
import com.example.coroutines.databinding.ActivityScopeBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext

class _7_JobsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScopeBinding
    private val TAG: String = "debug"

    private lateinit var job: Job

    val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + CoroutineName("Activity Scope") + CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception $throwable in context:$coroutineContext")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        example7Global()
    }

    private fun example1() {
        val job1 = CoroutineScope(Dispatchers.IO).launch {
            repeat(5){
                delay(1000L)
                Log.d("debug:", "Coroutine still running ")
            }
        }

        job1.invokeOnCompletion{
            println("debug: job1 canceled/Completed")
        }

//        val job2 = CoroutineScope(Dispatchers.IO).launch {
//            delay(10000L)
//        }
//
//        job2.invokeOnCompletion(){
//            println("debug: job2 canceled")
//        }

        binding.button2.setOnClickListener {
            job1.cancel(CancellationException("cancel job1"))
        }
    }

    private fun example2() {
        val job1 = CoroutineScope(Dispatchers.IO).launch {
            Log.d("debug", "Starting long running calculation ")
            for(i in 30..43){
                if(isActive)
                    Log.d("debug", "Result for $i : ${fib(i)}")
            }
            Log.d("debug", "Ending long running calculation ")
        }

        runBlocking {
            delay(1000)
            job1.cancel()
            Log.d("debug", "Canceled the JOB ")
        }
    }

    private fun example3TimeOut() {
        val job1 = CoroutineScope(Dispatchers.IO).launch {
            Log.d("debug", "Starting long running calculation ")
            withTimeout(2000L){
                for(i in 30..43){
                    if(isActive)
                        Log.d("debug", "Result for $i : ${fib(i)}")
                }
            }
            Log.d("debug", "Ending long running calculation ")
        }
    }

    private fun example4JobCancel() {
        val job1 = CoroutineScope(Dispatchers.IO).launch {
            repeat(5){
                delay(1000L)
                Log.d("debug:", "Coroutine 1 still running ")
            }
        }

        job1.invokeOnCompletion{
            println("debug: job1 canceled/Completed")
        }

        val job2 = CoroutineScope(Dispatchers.IO+job1).launch {
            repeat(5){
                delay(1000L)
                Log.d("debug:", "Coroutine 2 still running ")
            }
        }

        job2.invokeOnCompletion{
            println("debug: job2 canceled/Completed")
        }

        binding.button2.setOnClickListener {
            job1.cancel(CancellationException("cancel job1"))
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("Exception thrown in one of the children: $exception")
    }

    private fun example5JobCancelExp(){
        val parentJob = CoroutineScope(Dispatchers.IO).launch(handler) {

            // --------- JOB A ---------
            val jobA = launch {
                val resultA = getResult(1)
                println("resultA: ${resultA}")
            }
            jobA.invokeOnCompletion { throwable ->
                if(throwable != null){
                    println("Error getting resultA: ${throwable}")
                }
            }

            // --------- JOB B ---------
            val jobB = launch {
                val resultB = getResult(2)
                println("resultB: ${resultB}")
            }
            jobB.invokeOnCompletion { throwable ->
                if(throwable != null){
                    println("Error getting resultB: ${throwable}")
                }
            }

            // --------- JOB C ---------
            val jobC = launch {
                val resultC = getResult(3)
                println("resultC: ${resultC}")
            }
            jobC.invokeOnCompletion { throwable ->
                if(throwable != null){
                    println("Error getting resultC: ${throwable}")
                }
            }
        }
        parentJob.invokeOnCompletion { throwable ->
            if(throwable != null){
                println("Parent job failed: ${throwable}")
            }
            else{
                println("Parent job SUCCESS")
            }
        }
    }

    val childExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        println("Exception thrown in one of the children: $exception.")
    }

    fun example6Supervisor(){
        val parentJob = CoroutineScope(Main).launch(handler) {

            supervisorScope { // *** Make sure to handle errors in children ***

                // --------- JOB A ---------
                val jobA =  launch {
                    val resultA = getResult(1)
                    println("resultA: ${resultA}")
                }

                // --------- JOB B ---------
                val jobB = launch(childExceptionHandler) {
                    val resultB = getResult(2)
                    println("resultB: ${resultB}")
                }

                // --------- JOB C ---------
                val jobC = launch {
                    val resultC = getResult(3)
                    println("resultC: ${resultC}")
                }
            }
        }

        parentJob.invokeOnCompletion { throwable ->
            if(throwable != null){
                println("Parent job failed: ${throwable}")
            }
            else{
                println("Parent job SUCCESS")
            }
        }
    }

    fun example7Global(){
        val startTime = System.currentTimeMillis()
        println("Starting Parent Job")
        val parentJob = CoroutineScope(Main).launch(handler) {
                GlobalScope.launch {
                    work(1)
                }
                GlobalScope.launch {
                    work(2)
                }
//            delay(3000)
        }

        parentJob.invokeOnCompletion { throwable ->
            if(throwable != null){
                println("Job Was cancelled after ${System.currentTimeMillis() - startTime}  ms")
            }else{
                println("Job Was Done in ${System.currentTimeMillis() - startTime}  ms")
            }
        }

        binding.button2.setOnClickListener {
            parentJob.cancel()
        }
    }

    suspend fun work(i: Int){
        delay(3000)
        println("work done $i on ${Thread.currentThread().name}")
    }

    suspend fun getResult(number: Int): Int{
        return withContext(Dispatchers.Main){
            delay(number*500L)
            if(number == 2){
//                cancel(CancellationException("Error getting result for number: ${number}"))
//                throw CancellationException("Error getting result for number: ${number}") // treated like "cancel()"
                throw Exception("Error getting result for number: ${number}")
            }
            number*2
        }
    }


    private fun println(message: String){
        Log.d(TAG, message)
    }

    private fun fib(n:Int):Long{
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fib(n-1)+fib(n-2)
        }
    }
}