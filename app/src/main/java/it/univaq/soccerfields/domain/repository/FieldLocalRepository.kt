package it.univaq.soccerfields.domain.repository

import it.univaq.soccerfields.domain.model.Field
import kotlinx.coroutines.flow.Flow

interface FieldLocalRepository {
    suspend fun insert(field: Field)

    suspend fun insert(fields: List<Field>)

    suspend fun deleteAll()

    fun getAll(): Flow<List<Field>>
}