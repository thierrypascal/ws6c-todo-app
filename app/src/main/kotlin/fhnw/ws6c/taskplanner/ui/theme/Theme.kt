package fhnw.ws6c.taskplanner.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val appDarkColors = Colors(
    //Background colors
    primary          = Color(0xFF358FA8),
    primaryVariant   = Color(0xFF89BDCC),
    secondary        = Color(0xFF495568),
    secondaryVariant = Color(0xFF737C8B),
    background       = Color(0xFF192840),
    surface          = Color(0xFF192840),
    error            = Color(0xFFF44336),

    //Typography and icon colors
    onPrimary        = Color.White,
    onSecondary      = Color.White,
    onBackground     = Color.White,
    onSurface        = Color.White,
    onError          = Color.White,
    
    isLight = false
)

private val appLightColors = Colors(
    //Background colors
    primary          = Color(0xFF358FA8),
    primaryVariant   = Color(0xFF89BDCC),
    secondary        = Color(0xFFF3EFEF),
    secondaryVariant = Color(0xFFE7F0FF),
    background       = Color.White,
    surface          = Color.White,
    error            = Color(0xFFF44336),

    //Typography and icon colors
    onPrimary        = Color.White,
    onSecondary      = Color(0xFF303D52),
    onBackground     = Color(0xFF303D52),
    onSurface        = Color(0xFF303D52),
    onError          = Color.White,

    isLight = true
)

@Composable
fun AppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colors     = if (darkTheme) appDarkColors else appLightColors,
        content    = content
    )
}