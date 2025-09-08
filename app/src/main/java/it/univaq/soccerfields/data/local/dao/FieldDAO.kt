package it.univaq.soccerfields.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import it.univaq.soccerfields.data.local.entity.LocalField
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDAO {

    @Upsert
    suspend fun insert(field: LocalField)

    @Upsert
    suspend fun insert(fields: List<LocalField>)

    @Query("SELECT * FROM fields ORDER BY regione, citta ASC")
    fun getAll(): Flow<List<LocalField>>

    @Query("SELECT * FROM fields WHERE regione = :region AND citta = :city AND indirizzo = :address ORDER BY regione, citta ASC")
    fun getFieldByAddress(region: String, city: String, address: String): Flow<List<LocalField>>

    @Query("SELECT * FROM fields WHERE nome = :name AND citta = :city ORDER BY regione, citta ASC")
    fun getFieldByNameAndCity(name: String, city: String): Flow<List<LocalField>>

    @Query("DELETE FROM fields")
    suspend fun deleteAll()

}