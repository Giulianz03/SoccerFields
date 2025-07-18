package it.univaq.soccerfields.data.remote

import it.univaq.soccerfields.data.remote.model.RemoteField
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.repository.FieldRemoteRepository
import javax.inject.Inject

fun RemoteField.toModel(): Field = Field(
    id = null,
    nome = nome,
    citta = citta,
    regione = regione,
    latitudine = latitudine,
    longitudine = longitudine,
    larghezza = larghezza,
    lunghezza = lunghezza,
    tipo_superficie = tipo_superficie,
    capacita_spettatori = capacita_spettatori,
    indirizzo = indirizzo,
)

class FieldRetrofitRepository  @Inject constructor(
    private val FieldService: FieldService
): FieldRemoteRepository {
    override suspend fun getFields(): List<Field> {
        return FieldService.downloadData()
            .map(RemoteField::toModel)
    }
}