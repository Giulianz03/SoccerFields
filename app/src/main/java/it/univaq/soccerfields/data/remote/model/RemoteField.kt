package it.univaq.soccerfields.data.remote.model

class RemoteField (
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