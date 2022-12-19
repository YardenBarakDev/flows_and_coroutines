package com.ybdev.flowsandcoroutines.architecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ybdev.flowsandcoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel by viewModel<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        subscribe()
    }

    private fun subscribe() {
        //state flow
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.stateFlow.collectLatest {
                    setText(it)
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mViewModel.shareFlow.collectLatest {
                    setText(it)
                }
            }
        }
    }

    private fun setText(text: String) {
        val logicText = when(text.length){
            1 -> "1"
            2 -> "2"
            3 -> "3"
            else -> text
        }
        binding.myNetworkText.text = logicText
    }

    private fun initView() {
        setButtonListener()
    }

    private fun setButtonListener() {
        binding.button.setOnClickListener {
            mViewModel.makeNetworkCall()
        }
    }

}