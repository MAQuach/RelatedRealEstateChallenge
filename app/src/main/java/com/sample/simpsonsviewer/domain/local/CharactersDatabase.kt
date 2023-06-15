package com.sample.simpsonsviewer.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.simpsonsviewer.domain.local.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 2
)
abstract class CharactersDatabase : RoomDatabase() {
    abstract val charactersDao: CharactersDao
}
