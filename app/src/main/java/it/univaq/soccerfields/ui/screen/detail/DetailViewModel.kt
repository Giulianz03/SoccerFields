package it.univaq.soccerfields.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.repository.FieldLocalRepository
import it.univaq.soccerfields.ui.screen.list.ListUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val fields: List<Field> = emptyList(),
)

sealed class DetailEvent {
    data class OnFieldSelected(val name: String?, val address: String?, val city: String?, val region: String?): DetailEvent()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val localRepository: FieldLocalRepository,
): ViewModel() {
    var uiState by mutableStateOf(ListUiState())
        private set

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.OnFieldSelected -> {
                viewModelScope.launch {
                    localRepository.getFieldByName(
                        name = event.name ?: ""
                    ).collect {
                        uiState = uiState.copy(
                            fields = it,
                        )
                    }
                }
            }
        }
    }
}