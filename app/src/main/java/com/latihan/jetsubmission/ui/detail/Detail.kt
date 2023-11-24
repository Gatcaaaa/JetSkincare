package com.latihan.jetsubmission.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetsubmission.R
import com.latihan.jetsubmission.data.UiState
import com.latihan.jetsubmission.di.Injection
import com.latihan.jetsubmission.ui.ViewModelFactory
import com.latihan.jetsubmission.ui.component.ButtonOrder
import com.latihan.jetsubmission.ui.component.ProductCounter
import com.latihan.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun Detail(
    skincareId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
    navigateToKeranjang: () -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState){
            is UiState.Loading -> {
                viewModel.getSkincareById(skincareId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.item.image,
                    title = data.item.title,
                    basePoint = data.item.price,
                    count = data.count,
                    description = data.item.description,
                    onBackClick = navigateBack,
                    onAddToKeranjang = { count ->
                        viewModel.addToCart(data.item, count)
                        navigateToKeranjang()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    basePoint: Int,
    count: Int,
    description: String,
    onBackClick: () -> Unit,
    onAddToKeranjang: (count: Int) -> Unit,
    modifier: Modifier = Modifier
){
    var totalPrice by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
           Box {
               Image(
                   painter = painterResource(image),
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   modifier = modifier
                       .height(400.dp)
                       .fillMaxWidth()
                       .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
               )
               Icon(
                   imageVector = Icons.Default.ArrowBack,
                   contentDescription = stringResource(R.string.back),
                   modifier = Modifier
                       .padding(16.dp)
                       .clickable { onBackClick() }
               )
           }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = stringResource(R.string.required_harga, basePoint),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = {orderCount++},
                onProductDecreased = { if (orderCount > 0)orderCount--},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPrice = basePoint * orderCount
            ButtonOrder(
                text = stringResource(R.string.add_keranjang, totalPrice),
                enabled = orderCount > 0,
                onClick = {
                    onAddToKeranjang(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview(){
    JetSubmissionTheme {
        DetailContent(
            image = R.drawable.scarlett_res,
            title = "Scarlett Skincare",
            basePoint = 800000,
            count = 1,
            description = "bisa ngga sih ngertiin aku gitu sekaliu aja, aku tuh mau di beliin skincare scarlett ayolah harganya juga  ramah buat kantong mahasiswa kok",
            onBackClick = {},
            onAddToKeranjang = {}
        )
    }
}