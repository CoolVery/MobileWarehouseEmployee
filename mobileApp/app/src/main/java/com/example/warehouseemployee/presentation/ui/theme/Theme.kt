package com.example.warehouseemployee.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

object WarehouseEmployeeTheme {
    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current
    val colors: ColorPalette
        @ReadOnlyComposable
        @Composable
        get() = LocalColor.current
}


@Composable
fun WarehouseEmployeeTheme(
    themeMode: ThemeMode = ThemeMode.Light,
    typography: Typography = WarehouseEmployeeTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = when (themeMode) {
        ThemeMode.Dark -> darkTheme
        ThemeMode.Light -> lightTheme
    }
    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalColor provides colors,
    ) {
        content()
    }

}

sealed class ThemeMode(val title: String) {
    data object Dark: ThemeMode(title = "Dark")
    data object Light: ThemeMode(title = "Light")
}