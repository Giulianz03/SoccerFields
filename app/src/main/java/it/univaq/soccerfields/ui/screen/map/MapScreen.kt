package it.univaq.soccerfields.ui.screen.map

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import it.univaq.soccerfields.DetailActivity
import it.univaq.soccerfields.ui.tools.LifeCycleEvent
import it.univaq.soccerfields.ui.tools.LocationPermission
import it.univaq.soccerfields.ui.tools.PermissionChecker

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    //Mostra la mappa solo se i permessi sono stati accettati
    PermissionChecker(
        permission = LocationPermission(),
        events = listOf(
            LifeCycleEvent(event = Lifecycle.Event.ON_RESUME){
                viewModel.onEvent(MapEvent.StartLocation)
            },
            LifeCycleEvent(event = Lifecycle.Event.ON_PAUSE){
                viewModel.onEvent(MapEvent.StopLocation)
            }
        ),
    ) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = uiState.cameraPositionState,
        ) {
            //Mostro sulla mappa la mia posizione
            uiState.markerState?.let {
                Marker(
                    state = it,
                    title = "Current Location",
                    snippet = "Your Current Location",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                )
            }
            //Mostro sulla mappa i campi entro una certa distanza (filtredFields)
            uiState.filteredFields.forEach { field ->
                Marker(
                    state = rememberMarkerState(
                        position = LatLng(field.latitudine, field.longitudine)
                    ),
                    title = field.nome,
                    snippet = "${field.indirizzo}, ${field.citta}",
                    onInfoWindowClick = {
                        //Passaggio non da screen a screen ma verso una activity (non posso usare navcontroller)
                        context.startActivity(
                            Intent(context, DetailActivity::class.java)
                            .also {
                                it.putExtra("nome", field.nome)
                                it.putExtra("citta", field.citta)
                                it.putExtra("regione", field.regione)
                                it.putExtra("indirizzo", field.indirizzo)
                            }
                        )
                    }
                )
            }
        }
    }
}