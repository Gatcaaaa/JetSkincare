package com.latihan.jetsubmission.ui.keranjang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetsubmission.R
import com.latihan.jetsubmission.data.UiState
import com.latihan.jetsubmission.di.Injection
import com.latihan.jetsubmission.ui.ViewModelFactory
import com.latihan.jetsubmission.ui.component.ButtonOrder
import com.latihan.jetsubmission.ui.component.ItemKeranjang

@Composable
fun Keranjang(
    modifier: Modifier = Modifier,
    viewModel: KeranjangViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    onOrderButtonClicked: (String) -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getAddedOrderSkincare()
            }
            is UiState.Success -> {
                KeranjangItem(
                    state = uiState.data,
                    onProductCountChanged = { skincareId, count ->
                        viewModel.updateOrderSkincare(skincareId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeranjangItem(
    state: State,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderSkincare.count(),
        state.totalPrice
    )
    Column(modifier = modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.kerangjang),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ){
            items(state.orderSkincare, key = {it.item.id}){item ->
                ItemKeranjang(
                    rewardId = item.item.id,
                    image = item.item.image,
                    title = item.item.title,
                    totalPrice = item.item.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged
                )
                Divider()
            }
        }
        ButtonOrder(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderSkincare.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}