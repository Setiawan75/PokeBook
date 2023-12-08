package com.world.pokebook.screen.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.world.pokebook.R
import com.world.pokebook.databinding.ActivitySplashScreenBinding
import com.world.pokebook.screen.home.MainActivity

class SplashScreenView : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.btnStart.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
        }
    }
}