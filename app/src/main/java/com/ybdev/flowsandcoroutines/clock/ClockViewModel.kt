package com.ybdev.flowsandcoroutines.clock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClockViewModel: ViewModel() {

    //cold flow
    val countDownFlow = flow{
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }


    //hot flow
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun incrementCounter(){
        _stateFlow.value += 1
    }

    fun giveSameValue(){
        _stateFlow.value = -1
    }


    //hot flow
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun squareNumber(number: Int){
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }
}