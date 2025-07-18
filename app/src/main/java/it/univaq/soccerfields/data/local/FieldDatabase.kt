package it.univaq.soccerfields.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.univaq.soccerfields.data.local.dao.FieldDAO
import it.univaq.soccerfields.data.local.entity.LocalField

@Database(entities = [LocalField::class], version = 2, exportSchema = false)
abstract class FieldDatabase: RoomDatabase() {

    abstract fun getFieldDAO(): FieldDAO

}
