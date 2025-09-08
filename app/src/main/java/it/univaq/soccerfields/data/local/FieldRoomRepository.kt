package it.univaq.soccerfields.data.local

import it.univaq.soccerfields.data.local.dao.FieldDAO
import it.univaq.soccerfields.data.local.entity.LocalField
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.repository.FieldLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

fun LocalField.toModel() : Field = Field(
    id = id,
    nome = nome,
    citta = citta,
    regione = regione,
    latitudine = latitudine,
    longitudine = longitudine,
    larghezza = larghezza,
    lunghezza = lunghezza,
    tipo_superficie = tipo_superficie,
    capacita_spettatori = capacita_spettatori,
    indirizzo = indirizzo
)

fun Field.toLocal() : LocalField = LocalField(
    id = id,
    nome = nome,
    citta = citta,
    regione = regione,
    latitudine = latitudine,
    longitudine = longitudine,
    larghezza = larghezza,
    lunghezza = lunghezza,
    tipo_superficie = tipo_superficie,
    capacita_spettatori = capacita_spettatori,
    indirizzo = indirizzo
)

class FieldRoomRepository @Inject constructor(
    private val fieldDAO: FieldDAO
): FieldLocalRepository {

    override suspend fun insert(field: Field) {
        fieldDAO.insert(field.toLocal())
    }

    override suspend fun insert(fields: List<Field>) {
        fieldDAO.insert(fields.map(Field::toLocal))
    }

    override suspend fun deleteAll() {
        fieldDAO.deleteAll()
    }

    override fun getAll(): Flow<List<Field>> {
        return fieldDAO.getAll()
            .map { list ->
                list.map(LocalField::toModel)
            }
    }

    override fun getFieldByAddress(
        address: String,
        city: String,
        region: String
    ): Flow<List<Field>> {
        return fieldDAO.getFieldByAddress(region, city, address)
            .map { list ->
                list.map(LocalField::toModel)
            }
    }

    override fun getFieldByName(name: String): Flow<List<Field>> {
        return  fieldDAO.getFieldByName(name)
            .map { list ->
                list.map(LocalField::toModel)
            }
    }
}