package com.flow.hugh.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Movie(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: List<String>,
    val actor: List<String>,
    val userRating: String,
)

@Entity(tableName = "recent")
data class Recent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID") val ID: Int,
    @ColumnInfo(name = "query") val query: String,
)

data class SearchOption(
    val query: String,
    val genre: String? = null,
    val country: String? = null,
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
)

data class Genre(
    val code: String,
    val name: String,
)

data class Country(
    val code: String,
    val name: String,
)