package it.univaq.soccerfields.domain.model

data class Field(
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
