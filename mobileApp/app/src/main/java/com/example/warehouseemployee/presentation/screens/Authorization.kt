package com.example.warehouseemployee.presentation.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization (
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    Text(
        text = "as"
    )
}