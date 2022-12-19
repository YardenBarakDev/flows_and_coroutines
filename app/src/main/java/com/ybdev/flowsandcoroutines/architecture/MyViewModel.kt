package com.ybdev.flowsandcoroutines.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybdev.flowsandcoroutines.architecture.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyViewModel(private val myRepositoryImpl: MyRepository) : ViewModel() {

    private val _stateFlow = MutableStateFlow("")
    val stateFlow = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<String>()
    val shareFlow = _shareFlow.asSharedFlow()

    fun makeNetworkCall() {
        viewModelScope.launch(Dispatchers.IO) {
            myRepositoryImpl.fakeNetworkCall().collectLatest {
                _stateFlow.value = it
                //_shareFlow.emit(it)
            }
        }
    }
}