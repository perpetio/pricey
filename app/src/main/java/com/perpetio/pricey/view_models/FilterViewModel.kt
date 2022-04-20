package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.ProductArticle

class FilterViewModel: ViewModel() {
    val selectedArticle = mutableStateOf<ProductArticle?>(null)
}