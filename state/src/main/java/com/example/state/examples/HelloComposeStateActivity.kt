package com.example.state.examples

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.state.databinding.ActivityHelloComposeStateBinding

/**
 * @author AlexisYin
 */
class HelloComposeStateActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editText.doAfterTextChanged {
            binding.textView.text = "Hello, ${it.toString()}"
        }
    }
}

class HelloViewModel : ViewModel() {
    //MutableLiveData可读写，LiveData只读，确保只能使用提供的方法去修改
    // （暴露的name只读，要修改只能调用onNameChange）
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}

class HelloComposeStateActivityWithViewModel : AppCompatActivity() {
    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editText.doAfterTextChanged {
            viewModel.onNameChange(it.toString())
        }
        viewModel.name.observe(this) { name ->
            binding.textView.text = "Hello, $name"
        }
    }
}