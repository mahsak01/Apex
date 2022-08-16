package com.example.apex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

    }
}

//        val navHostFragment = supportFragmentManager.findFragmentById(
//            R.id.activityMain_navHostContainer
//        ) as NavHostFragment
//this.findNavController().navigate(R.id.action_chequeFragment_to_mainFragment)
//this.requireActivity().onBackPressed()