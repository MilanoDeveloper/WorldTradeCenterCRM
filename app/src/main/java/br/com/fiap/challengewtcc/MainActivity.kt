package br.com.fiap.challengewtcc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import br.com.fiap.challengewtcc.ui.theme.ChallengewtccTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengewtccTheme {
                Surface { AppNavGraph() }
            }
        }
    }
}