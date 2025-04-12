package com.harsh.harshpahurkar_assignment4

import com.harsh.harshpahurkar_assignment4.Product
import com.harsh.harshpahurkar_assignment4.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun getFirstProduct(): Product? {
        return productDao.getFirstProduct()
    }

    suspend fun getLastProduct(): Product? {
        return productDao.getLastProduct()
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }
}