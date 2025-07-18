package it.univaq.soccerfields.ui.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import it.univaq.soccerfields.domain.model.Field

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    if (uiState.loadingMessage != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = uiState.loadingMessage)
        }
        return
    }

    if (uiState.error != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = uiState.error)
        }
        return
    }

    Column (modifier = modifier) {

        Text(
            text = "Soccer Fields",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(uiState.fields.size) { index ->
                val field = uiState.fields[index]
                FieldItem(
                    modifier = Modifier.fillMaxWidth(),
                    field = field,
                    onItemClick = {
                        //TODO: handle item click
                    }
                )
            }
        }
    }
}

@Composable
fun FieldItem(
    modifier: Modifier = Modifier,
    field: Field,
    onItemClick: (Field) -> Unit = {}
) {
    Column (
        modifier = modifier
            .padding(15.dp)
            .clickable {
                onItemClick(field)
            }
    ){
        Text(
            text = field.nome,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${field.indirizzo}, ${field.citta}, ${field.regione}",
            style = MaterialTheme.typography.bodyMedium
        )
    }

}