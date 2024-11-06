package com.example.warehouseemployee.presentation.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
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
fun PhoneTextField (value: String, funChange: (String) -> Unit, placeholder: String) {

    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    OutlinedTextField(
        value = textFieldValue,
        //-----
        //Далее проверка на то, что длина нового вводимого текста не больше 12 и совпадает с рег
        //Главная проверка - если длина значения в текстовом поле меньеш 2 (т.е. пользователь удаляет +7),
        //То он кладет в TextFieldValue +7 и ставит курсор после 7
        //Иначе, он создает регулярное значение, что введены только цифры, далее проверяет,
        //Что значение в текстовом поле меньше 12 и подходит под регулярное выражение
        //Если да, то меняется значение поля, а новое значение записывается в phone из viewmodel
        onValueChange = { newValue ->
            if (newValue.text.length < 2) {
                textFieldValue = TextFieldValue("+7", selection = TextRange(2))
            }
            else {
                val regex = Regex("^[+\\d]*$")
                if (newValue.text.length <= 12 && regex.matches(newValue.text)) {
                    textFieldValue = newValue
                    funChange(textFieldValue.text.substring(1))
                }
            }
        },
        //-----
        //Открывается клавиатура с цифрами
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        textStyle = WarehouseEmployeeTheme.typography.textField,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            //Проверяет, если поле попало в фокус и значение в поле пустое
            //Если да, то значение в поле добавляется +7, а в phone из viewmodel передается 7
            .onFocusChanged { focusState ->
                if (focusState.isFocused && value.isEmpty()) {
                    funChange("7")
                    textFieldValue = TextFieldValue("+7", selection = TextRange(2))
                }
            },
        placeholder = {
            Text(
                text = placeholder
            )
        },
        minLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
    )
}