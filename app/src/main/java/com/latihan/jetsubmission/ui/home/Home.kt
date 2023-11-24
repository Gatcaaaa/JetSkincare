package com.latihan.jetsubmission.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetsubmission.data.UiState
import com.latihan.jetsubmission.di.Injection
import com.latihan.jetsubmission.model.OrderSkincare
import com.latihan.jetsubmission.ui.ViewModelFactory
import com.latihan.jetsubmission.ui.component.ItemSkincare


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel:HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getAllSkincare()
            }
            is UiState.Success -> {
                HomeContent(orderSkincare = uiState.data, navigateToDetail = navigateToDetail, modifier = modifier)
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun HomeContent(
    orderSkincare: List<OrderSkincare>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ){
        items(orderSkincare){ data ->
            ItemSkincare(
                image = data.item.image, title = data.item.title, requiredPoint = data.item.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.item.id)
                }
            )
        }
    }
}