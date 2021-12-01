package com.example.coroutines.sample1;

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class _2_ScopeViewModel : ViewModel() {

    private val _result = MutableLiveData<String>()
//    val result: LiveData<String> = _result
    val result: LiveData<String> = liveData(Dispatchers.IO) {
        emit(doComputation())
    }

//    init {
//        viewModelScope.launch {
//            val computationResult = doComputation()
//            _result.value = computationResult
//        }
//    }

//    init {
//        viewModelScope.launch {
//            while(true){
//                delay(500)
//                Log.e("debug", "view model scope running " )
//            }
//        }
//    }

    suspend fun doComputation():String{
        delay(3000)
        return "result"
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("debug", "viewmodel onCleared " )
    }
}
