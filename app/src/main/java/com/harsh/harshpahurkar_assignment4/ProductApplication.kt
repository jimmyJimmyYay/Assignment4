package com.harsh.harshpahurkar_assignment4

import android.app.Application
import com.harsh.harshpahurkar_assignment4.ProductDatabase
import com.harsh.harshpahurkar_assignment4.ProductRepository

class ProductApplication : Application() {
    val database by lazy { ProductDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}