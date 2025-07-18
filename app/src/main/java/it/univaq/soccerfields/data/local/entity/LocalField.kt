package it.univaq.soccerfields.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class LocalField(
    @PrimaryKey
    val id: Int?,
    val nome: String,
    val citta: String,
    val regione: String,
    val latitudine: Long,
    val longitudine: Long,
    val larghezza: Int,
    val lunghezza: Int,
    val tipo_superficie: String,
    val capacita_spettatori: Int,
    val indirizzo: String,
)
