package com.xiaoyu.verificationcodeframe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xiaoyu.verificationcodeframe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel =
                ViewModelProvider(
                    this@MainActivity,
                    ViewModelProvider.NewInstanceFactory()
                ).get("MainViewModel", MainViewModel::class.java).apply {
                    showToast.observe(this@MainActivity, Observer {
                        Toast.makeText(this@MainActivity, "输入结束 $it", Toast.LENGTH_SHORT).show()
                    })
                }
        }
        setContentView(mMainBinding.root)
    }
}