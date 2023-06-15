package com.sample.simpsonsviewer.domain.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.sample.simpsonsviewer.domain.apimodel.RelatedTopic
import java.util.regex.Pattern

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey() val name: String,
    val firstURL: String,
    val icon: String,
    val text: String,
)

fun List<RelatedTopic>.mapToCharacterEntity(): List<CharacterEntity> =
    this.map {
        val iconJson = Gson().toJson(it.icon)
        CharacterEntity(
            firstURL = it.firstURL,
            icon = iconJson,
            name = getCharacterName(it.result),
            text = it.text
        )
    }

fun getCharacterName(text: String): String {
    var characterName: String = ""
    val pattern = Pattern.compile("<.*?>(.*?)</a>")
    val matcher = pattern.matcher(text)

    characterName = if (matcher.find()) {
        matcher.group(1)
    } else {
        text
    }

    return characterName
}
