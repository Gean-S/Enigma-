package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppDatabase
import com.example.data.GameRepository
import com.example.ui.AppNavigation
import com.example.ui.GameViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = AppDatabase.getDatabase(this)
    val repository = GameRepository(database.userProgressDao())

    setContent {
      val viewModel: GameViewModel = viewModel(
          factory = object : ViewModelProvider.Factory {
              override fun <T : ViewModel> create(modelClass: Class<T>): T {
                  return GameViewModel(repository) as T
              }
          }
      )

      MyApplicationTheme {
        AppNavigation(viewModel = viewModel)
      }
    }
  }
}
