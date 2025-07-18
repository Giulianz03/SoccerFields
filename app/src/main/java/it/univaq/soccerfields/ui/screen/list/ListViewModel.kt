package it.univaq.soccerfields.ui.screen.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.univaq.soccerfields.common.Resource
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.use_case.GetFieldsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListUiState(
    val fields: List<Field> = emptyList(),
    val loadingMessage: String? = null,
    val error: String? = null
)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val getFieldsUseCase: GetFieldsUseCase
): ViewModel() {
    var uiState by mutableStateOf(ListUiState())
        private set

    init {
        downloadFields()
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