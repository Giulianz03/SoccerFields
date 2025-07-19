package it.univaq.soccerfields.ui.screen.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    GoogleMap(
        modifier = modifier,
    ){
        uiState.fields.forEach{ field ->
            Marker(
                state = rememberMarkerState(
                    position = LatLng(field.latitudine, field.longitudine)
                ),
                title = field.nome,
                snippet = "${field.indirizzo}, ${field.citta}"
            )
        }
    }
}