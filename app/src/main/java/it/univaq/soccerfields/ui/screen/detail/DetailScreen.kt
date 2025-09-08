package it.univaq.soccerfields.ui.screen.detail

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.ui.screen.list.ListViewModel
import it.univaq.soccerfields.ui.tools.LifeCycleEvent

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    nome: String?,
    citta: String?
){
    val activity = LocalContext.current as Activity
    val uiState = viewModel.uiState

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.onEvent(
            DetailEvent.OnFieldSelected(
                name = nome,
                city = citta
            )
        )
    }

    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = {activity.finish()} ) {
                        Icon(contentDescription = "Back", imageVector = Icons.AutoMirrored.Default.ArrowBack)
                    }
                }
            )
        }
    ){ padding ->

        if (uiState.fields.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No fields found")
            }
            return@Scaffold
        }
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(uiState.fields.size) {
                FieldItem(
                    modifier = Modifier.fillMaxWidth(),
                    field = uiState.fields[it],
                )
            }
        }
    }
}

@Composable
fun FieldItem(
    modifier: Modifier = Modifier,
    field: Field,
){
    Card (
        modifier = modifier
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
        ) {
            Text(text = "Nome: ${field.nome}")
            Text(text = "Indirizzo: ${field.indirizzo}")
            Text(text = "Città: ${field.citta}, ${field.regione}")
            Text(text = "Dimensioni: ${field.larghezza} x ${field.lunghezza}")
            Text(text = "Terreno: ${field.tipo_superficie}")
            Text(text = "Max Spettatori: ${field.capacita_spettatori}")
        }
    }
}