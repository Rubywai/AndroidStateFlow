package com.example.androidstateflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _uiFlow : MutableStateFlow<MyState> = MutableStateFlow(MyState.Empty)
    val uiFlow : StateFlow<MyState>
     get() = _uiFlow
    fun setState(state : MyState) = viewModelScope.launch{
        when(state){
            is MyState.Loading->{
               _uiFlow.value = MyState.Loading
            }
            MyState.Empty -> MyState.Empty
            is MyState.Success ->{
                _uiFlow.value = MyState.Loading
                delay(2000)
                _uiFlow.value = MyState.Success("Success")
            }
            is MyState.Failed -> _uiFlow.value = MyState.Failed("Error")
        }
    }
}