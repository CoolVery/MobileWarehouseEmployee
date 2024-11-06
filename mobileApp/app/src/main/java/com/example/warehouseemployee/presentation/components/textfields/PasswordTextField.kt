package com.example.warehouseemployee.presentation.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.warehouseemployee.R
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme


/**
 * Текстовые поля для авторизации
 *
 * @param value Значение, которое сохраяняет, что написано в текстовом поле
 * @param funChange Функция, которая кладет значение в переменную из viewmodel
 * @param placeholder Плейсхолдер текстового поля
 *
 * @return Текстовое поле для авторизации
 */
@Composable
fun PasswordTextField (value: String, funChange: (String) -> Unit, placeholder: String) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation('*'),
        textStyle = WarehouseEmployeeTheme.typography.textField.copy(
            textDecoration = TextDecoration.None
        ),
        leadingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                if (passwordVisibility) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = "Кнопка для показа пароля"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_off),
                        contentDescription = "Кнопка для скрытия пароля"
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
        placeholder = {
            Text(
                text = placeholder
            )
        },
        minLines = 1,

        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedSuffixColor = Color.Transparent,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent
        ),
    )
}