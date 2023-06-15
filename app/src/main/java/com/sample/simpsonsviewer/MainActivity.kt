package com.sample.simpsonsviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sample.simpsonsviewer.databinding.ActivityMainBinding
import com.sample.simpsonsviewer.databinding.ActivityMainTabletBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bindingTablet by lazy {
        ActivityMainTabletBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = resources.configuration
        val screenWidthDp = configuration.screenWidthDp

        Log.d(TAG, "onCreate: Screen Width: $screenWidthDp")
        // determine if it's a phone or tablet
        if (screenWidthDp < 600) {
            setContentView(binding.root)
        } else {
            setContentView(bindingTablet.root)
        }

        var navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
    }
}
