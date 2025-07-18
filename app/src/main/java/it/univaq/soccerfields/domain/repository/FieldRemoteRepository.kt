package it.univaq.soccerfields.domain.repository

import it.univaq.soccerfields.domain.model.Field

interface FieldRemoteRepository {

    suspend fun getFields(): List<Field>
}