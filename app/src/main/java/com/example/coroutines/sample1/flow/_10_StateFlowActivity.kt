package com.example.coroutines.sample1.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.coroutines.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class _10_StateFlowActivity : AppCompatActivity() {

    private val viewModel: _10_StateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        viewModel.login("android", "password")

        lifecycleScope.launchWhenCreated {
            viewModel.loginUIState.collect {
                when(it){
                    is _10_StateViewModel.LoginUIState.Loading -> Log.e("debug", "Loading" )
                    is _10_StateViewModel.LoginUIState.Error -> Log.e("debug", it.message )
                    is _10_StateViewModel.LoginUIState.Success ->   Log.e("debug", "Success" )
                    is _10_StateViewModel.LoginUIState.Empty -> Unit
                }
            }
        }

    }
}