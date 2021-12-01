package com.example.coroutines.sample1.retro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutines.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class _8_RetroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8_retro)

        RetrofitInstance.api.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        for (todo in it)
                            Log.d("debug", todo.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Log.e("debug", "onFailure: $t" )
            }

        })

        /*lifecycleScope.launchWhenCreated {
            val response = RetrofitInstance.api.getTodos()
            try {
                if(response.isSuccessful){
                    response.body()?.let {
                        for (todo in it)
                            Log.d("debug", todo.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("debug", "onFailure: $e" )
            }
        }*/
    }
}