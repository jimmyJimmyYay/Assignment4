package com.harsh.harshpahurkar_assignment4

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val allProducts: Flow<List<Product>> = repository.allProducts

    private val _currentEditProduct = MutableLiveData<Product?>()
    val currentEditProduct: LiveData<Product?> = _currentEditProduct

    fun addProduct(title: String, description: String) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            val product = Product(title = title, description = description)
            viewModelScope.launch {
                repository.insertProduct(product)
            }
        }
    }

    fun deleteFirstProduct() {
        viewModelScope.launch {
            val firstProduct = repository.getFirstProduct()
            firstProduct?.let {
                repository.deleteProduct(it)
            }
        }
    }

    fun getLastProductForEdit() {
        viewModelScope.launch {
            _currentEditProduct.value = repository.getLastProduct()
        }
    }

    fun updateProduct(id: Int, title: String, description: String) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            val updatedProduct = Product(id = id, title = title, description = description)
            viewModelScope.launch {
                repository.updateProduct(updatedProduct)
                _currentEditProduct.value = null // Reset after update
            }
        }
    }

    fun clearEditState() {
        _currentEditProduct.value = null
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}