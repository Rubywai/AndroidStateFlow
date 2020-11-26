package com.example.androidstateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidstateflow.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val myViewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener{
                myViewModel.setState(MyState.Loading)
            }
            button2.setOnClickListener{
                myViewModel.setState(MyState.Success("Hello"))
            }
            button3.setOnClickListener{
                myViewModel.setState(MyState.Failed("Error"))
            }
        }

        lifecycleScope.launchWhenStarted {
            myViewModel.uiFlow.collect {
                when (it) {
                    MyState.Empty -> {

                    }
                    MyState.Loading -> {
                        activityMainBinding.apply {
                            progressBar.visibility = View.VISIBLE
                            success.visibility = View.GONE
                            failed.visibility = View.GONE

                        }
                    }
                    is MyState.Success -> {
                        activityMainBinding.apply {
                            success.text = it.message
                            progressBar.visibility = View.GONE
                            success.visibility = View.VISIBLE
                            failed.visibility = View.GONE
                        }
                    }

                    is MyState.Failed -> {
                        activityMainBinding.apply {
                            success.text = it.error ?: "none"
                            progressBar.visibility = View.GONE
                            success.visibility = View.VISIBLE
                            failed.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}

