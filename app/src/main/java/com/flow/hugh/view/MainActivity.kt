package com.flow.hugh.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.flow.hugh.R
import com.flow.hugh.databinding.ActivityMainBinding
import com.flow.hugh.toast
import com.flow.hugh.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationHost) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - waitTime >= 1000 ) {
            waitTime = System.currentTimeMillis()
            toast(this, getString(R.string.msg_back))
        } else {
            finish()
        }
    }
}