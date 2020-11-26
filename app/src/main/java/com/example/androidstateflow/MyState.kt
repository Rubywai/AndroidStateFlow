package com.example.androidstateflow

sealed class MyState {
    object Empty : MyState()
    object Loading : MyState()
    class Success(val message : String) : MyState()
    class Failed(val error : String) : MyState()
}