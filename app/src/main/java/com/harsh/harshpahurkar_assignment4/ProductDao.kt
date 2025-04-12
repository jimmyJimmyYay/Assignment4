package com.harsh.harshpahurkar_assignment4

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM `product-details` ORDER BY id ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM `product-details` ORDER BY id ASC LIMIT 1")
    suspend fun getFirstProduct(): Product?

    @Query("SELECT * FROM `product-details` ORDER BY id DESC LIMIT 1")
    suspend fun getLastProduct(): Product?

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}