package br.com.fiap.challengewtcc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Light = lightColorScheme(
    primary = Color(0xFF6E56CF),
    secondary = Color(0xFF7C83FD),
    primaryContainer = Color(0xFFE8E0FF),
    surfaceVariant = Color(0xFFF1F1F6)
)

private val Dark = darkColorScheme(
    primary = Color(0xFF9E8CFF),
    secondary = Color(0xFFA3A9FF),
    primaryContainer = Color(0xFF3B3761),
    surfaceVariant = Color(0xFF2A2A33)
)

@Composable
fun ChallengewtccTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) Dark else Light
    MaterialTheme(colorScheme = colors, typography = Typography, content = content)
}
