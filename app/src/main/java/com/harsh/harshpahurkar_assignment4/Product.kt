package com.harsh.harshpahurkar_assignment4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product-details")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)