package com.example.warehouseemployee.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization (
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarehouseEmployeeTheme.colors.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp, 50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_important_element)
            ) {
                Text(
                    text = "Авторизация",
                    style = WarehouseEmployeeTheme.typography.titleLarge,
                    color = WarehouseEmployeeTheme.colors.text_color_important_element

                )
            }

        }
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(30.dp, 0.dp)
        ) {
            Text(
                text = "Телефон",
                style = WarehouseEmployeeTheme.typography.bodyLarge,
                color = WarehouseEmployeeTheme.colors.text_color_second_element,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Divider(
                color = Color.White, // Цвет линии
                thickness = 3.dp, // Толщина линии
                modifier = Modifier.fillMaxWidth() // Занять всю ширину
            )
            OutlinedTextField (
                value = "",
                textStyle = WarehouseEmployeeTheme.typography.titleLarge,
                onValueChange = {  },
                modifier = Modifier
                    .background(Color.White)
                )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 0.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarehouseEmployeeTheme.colors.background_important_element,
                    contentColor = WarehouseEmployeeTheme.colors.text_color_important_element
                ),
                contentPadding = PaddingValues(vertical = 30.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Вход",
                    style = WarehouseEmployeeTheme.typography.titleLarge,
                    color = WarehouseEmployeeTheme.colors.text_color_important_element
                )
            }
        }

    }
}