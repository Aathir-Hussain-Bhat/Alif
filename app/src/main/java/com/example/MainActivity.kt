package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.alif.data.AppDatabase
import com.example.alif.data.AppRepository
import com.example.alif.ui.AppNavigation
import com.example.alif.ui.MainViewModel
import com.example.alif.ui.MainViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(this)
        val repository = AppRepository(database.appDao())
        val factory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
