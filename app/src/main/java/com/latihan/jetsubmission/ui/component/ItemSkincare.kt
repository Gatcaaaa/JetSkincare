package com.latihan.jetsubmission.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.latihan.jetsubmission.R
import com.latihan.jetsubmission.ui.theme.JetSubmissionTheme
import com.latihan.jetsubmission.ui.theme.Shapes


@Composable
fun ItemSkincare(
    image: Int,
    title: String,
    requiredPoint: Int,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )

        Text(
            text = stringResource(R.string.required_harga, requiredPoint),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ItemSkincarePreview(){
    JetSubmissionTheme {
        ItemSkincare(R.drawable.originote_res, "Originote Skincare", 500000)
    }
}