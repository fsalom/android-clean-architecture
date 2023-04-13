package es.rudo.androidbaseproject.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.rudo.androidbaseproject.data.model.CharacterDbModel

@Database(entities = [CharacterDbModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}