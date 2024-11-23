package com.example.warehouseemployee.presentation.screens.auth

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.components.textfields.PasswordTextField
import com.example.warehouseemployee.presentation.components.textfields.PhoneTextField
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.json.Json


@Composable
fun Authorization (
    navController: NavHostController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val phone = viewModel.phone.collectAsState(initial = "")
    val password = viewModel.password.collectAsState(initial = "")

    //Лаунсэффект, который срабатывает при изменении isError
    //Если в ошибка не пустая строка и не начинается с S, то появится тост
    //Иначе если ошибка не пустая, т.е. в ней содержится айди пользователя, то произойдет навигация
    LaunchedEffect(key1 = viewModel.isError) {
        viewModel.isError.collect { error ->
            error?.let {
                if(!error.startsWith("S") && error != "") {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
                else if(error != "") {
                    viewModel.navigateTo.collect { route ->
                        route?.let {
                            viewModel.worker.collect { user ->
                                if (route == TasksWorkerDestination.route) {
                                    navController.navigate("${route}/${user}/null")

                                }
                                else {
                                    navController.navigate("${route}/${user}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(phone.value) {
        if (phone.value.length == 12) {
            focusManager.moveFocus(FocusDirection.Next)
        }
    }
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
                    style = WarehouseEmployeeTheme.typography.primaryTitle,
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
                style = WarehouseEmployeeTheme.typography.smallText,
                color = WarehouseEmployeeTheme.colors.text_color_second_element,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Divider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            )
            PhoneTextField(
                value = phone.value,
                funChange =  { newPhone -> viewModel.onPhoneChange(newPhone) },
                placeholder = "Телефон")
            Text(
                text = "Пароль",
                style = WarehouseEmployeeTheme.typography.smallText,
                color = WarehouseEmployeeTheme.colors.text_color_second_element,
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 30.dp)
            )
            Divider(
                color = Color.White, // Цвет линии
                thickness = 3.dp, // Толщина линии
                modifier = Modifier
                    .fillMaxWidth() // Занять всю ширину
                    .padding(bottom = 30.dp)
            )
            PasswordTextField(
                value = password.value,
                funChange = { newPassword -> viewModel.onPasswordChange(newPassword) },
                placeholder = "Пароль")
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
                onClick = {
                    if (password.value.isEmpty() || phone.value.isEmpty()) {
                        Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_LONG).show()
                    }
                    else viewModel.onSignIn()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarehouseEmployeeTheme.colors.background_important_element,
                    contentColor = WarehouseEmployeeTheme.colors.text_color_important_element
                ),
                contentPadding = PaddingValues(vertical = 30.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Вход",
                    style = WarehouseEmployeeTheme.typography.primaryTitle,
                    color = WarehouseEmployeeTheme.colors.text_color_important_element
                )
            }
        }

    }
}