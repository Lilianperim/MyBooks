package br.edu.scl.ifsp.sdm.mybooks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var genre: String,
    var author: String
)
