package com.ybdev.flowsandcoroutines.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.ybdev.flowsandcoroutines.databinding.ActivityClockBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class ClockActivity : AppCompatActivity() {

    private lateinit var binding : ActivityClockBinding
    val mViewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //clock()
        stateFlow()
        sharedFlow()
    }

    private fun sharedFlow() {
        binding.clockActivitySharedFlowButton.setOnClickListener {
            mViewModel.squareNumber(Random.nextInt(2, 5))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mViewModel.sharedFlow.collect{
                    binding.clockActivitySharedFlowText.text = it.toString()
                    Snackbar.make(binding.root, "Hello From Shared Flow",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun stateFlow() {
        binding.clockActivityStateFlowButton.setOnClickListener {
            //mViewModel.incrementCounter()
            mViewModel.giveSameValue()
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
             mViewModel.stateFlow
                 .collect{
                     //binding.clockActivityStateFlowText.text = it.toString()
                     Snackbar.make(binding.root, "Hello From State Flow",Snackbar.LENGTH_LONG).show()
             }
            }
        }
    }

    private fun clock() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                mViewModel.countDownFlow
                    .filter { time ->
                        time % 2 == 0
                    }
                    .map { time ->
                        time * time
                    }
                    .collect { filteredTime ->
                        binding.clockActivityCountdownText.text = filteredTime.toString()
                    }
            }
        }

        collectLatestLifecycleFlow(mViewModel.sharedFlow){

        }
    }

    fun <T> collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
        lifecycleScope.launch(){
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collectLatest(collect)
            }
        }
    }
}

