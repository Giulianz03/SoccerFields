package it.univaq.soccerfields.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields", primaryKeys = ["nome", "citta"])
data class LocalField(
    val id: Int?,
    val nome: String,
    val citta: String,
    val regione: String,
    val latitudine: Double,
    val longitudine: Double,
    val larghezza: Int,
    val lunghezza: Int,
    val tipo_superficie: String,
    val capacita_spettatori: Int,
    val indirizzo: String,
)
