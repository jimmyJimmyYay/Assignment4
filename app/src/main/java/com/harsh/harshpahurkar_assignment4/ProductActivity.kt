package com.harsh.harshpahurkar_assignment4

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapp.databinding.ActivityProductBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val productAdapter = ProductAdapter()

    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = productAdapter
        }
    }

    private fun setupClickListeners() {
        // Save button
        binding.saveButton.setOnClickListener {
            val title = binding.productTitleInput.text.toString().trim()
            val description = binding.productDescriptionInput.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                productViewModel.addProduct(title, description)
                clearInputFields()
                Toast.makeText(this, getString(R.string.product_saved), Toast.LENGTH_SHORT).show()
            }
        }

        // Delete first button
        binding.deleteFirstButton.setOnClickListener {
            productViewModel.deleteFirstProduct()
        }

        // Edit last button
        binding.editLastButton.setOnClickListener {
            productViewModel.getLastProductForEdit()
        }

        // Update button
        binding.updateButton.setOnClickListener {
            val title = binding.productTitleInput.text.toString().trim()
            val description = binding.productDescriptionInput.text.toString().trim()

            productViewModel.currentEditProduct.value?.let { product ->
                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                } else {
                    productViewModel.updateProduct(product.id, title, description)
                    Toast.makeText(this, getString(R.string.product_updated), Toast.LENGTH_SHORT).show()
                    switchToAddMode()
                }
            }
        }

        // Cancel button
        binding.cancelButton.setOnClickListener {
            productViewModel.clearEditState()
            switchToAddMode()
        }
    }

    private fun observeViewModel() {
        // Observe products list
        lifecycleScope.launch {
            productViewModel.allProducts.collectLatest { products ->
                productAdapter.submitList(products)
            }
        }

        // Observe edit state
        productViewModel.currentEditProduct.observe(this) { product ->
            product?.let {
                switchToEditMode(it.title, it.description)
            }
        }
    }

    private fun switchToEditMode(title: String, description: String) {
        binding.apply {
            formTitleText.text = getString(R.string.edit_product)
            productTitleInput.setText(title)
            productDescriptionInput.setText(description)
            saveButton.visibility = View.GONE
            updateButton.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
        }
    }

    private fun switchToAddMode() {
        binding.apply {
            formTitleText.text = getString(R.string.add_product)
            clearInputFields()
            saveButton.visibility = View.VISIBLE
            updateButton.visibility = View.GONE
            cancelButton.visibility = View.GONE
        }
    }

    private fun clearInputFields() {
        binding.apply {
            productTitleInput.setText("")
            productDescriptionInput.setText("")
        }
    }
}