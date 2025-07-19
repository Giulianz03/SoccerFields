package it.univaq.soccerfields.ui.screen.map

import android.util.Log
import android.content.Context
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.univaq.soccerfields.common.LocationHelper
import it.univaq.soccerfields.common.Resource
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.use_case.GetFieldsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState(
    val fields: List<Field> = emptyList(),
    val loadingMessage: String? = null,
    val error: String? = null,
    val markerState: MarkerState? = null,
    val filteredFields: List<Field> = emptyList(),
)

sealed class MapEvent {
    data object StartLocation: MapEvent()
    data object StopLocation: MapEvent()
}

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getFieldsUseCase: GetFieldsUseCase,
): ViewModel() {
    var uiState by mutableStateOf(MapUiState())
        private set

    private val locationHelper = LocationHelper(context = context){ location ->
        Log.d("LocationDebug", "Lat: ${location.latitude}, Lng: ${location.longitude}")
        //Creo un markerState con la mia posizione
        val markerState = MarkerState(
            position = LatLng(location.latitude, location.longitude)
        )
        //Filtro i campi entro una certa distanza dalla mia posizione
        val filtredfields = uiState.fields.filter {
            val fieldLocation = Location("field").apply {
                latitude = it.latitudine
                longitude = it.longitudine
            }
            location.distanceTo(fieldLocation) <= 10000
        }
        //Passo allo uiState sia la mia posizione che la lista filtrata
        uiState = uiState.copy(
            markerState = markerState,
            filteredFields = filtredfields
        )
    }

    init {
        downloadFields()
    }

    fun onEvent(event: MapEvent) {
        when(event){
            is MapEvent.StartLocation -> {
                locationHelper.stop()
            }
            is MapEvent.StopLocation -> {
                locationHelper.stop()
            }
        }
    }

    private fun downloadFields() {
        viewModelScope.launch {
            getFieldsUseCase().collect { resource ->
                when(resource){
                    is Resource.Loading -> {
                        uiState = uiState.copy(
                            loadingMessage = resource.message,
                            error = null,
                        )
                    }
                    is Resource.Success -> {
                        uiState = uiState.copy(
                            fields = resource.data,
                            loadingMessage = null,
                            error = null,
                        )
                    }
                    is Resource.Error -> {
                        uiState = uiState.copy(
                            loadingMessage = null,
                            error = resource.message,
                        )
                    }
                }
            }
        }
    }
}