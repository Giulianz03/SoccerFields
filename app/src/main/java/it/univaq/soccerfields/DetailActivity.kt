package it.univaq.soccerfields

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import it.univaq.soccerfields.ui.screen.detail.DetailScreen
import it.univaq.soccerfields.ui.theme.SoccerFieldsTheme

@AndroidEntryPoint
class DetailActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoccerFieldsTheme {
                val nome = intent.getStringExtra("nome")
                val citta = intent.getStringExtra("citta")
                val regione = intent.getStringExtra("regione")
                val indirizzo = intent.getStringExtra("indirizzo")
                DetailScreen(
                    nome = nome,
                    citta = citta,
                    regione = regione,
                    indirizzo = indirizzo
                )
            }
        }
    }
}