package com.latihan.jetsubmission.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.latihan.jetsubmission.R
import com.latihan.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun Profile(){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.aleg_res),
            contentDescription = stringResource(R.string.profile_alega),
            modifier = Modifier
                .size(390.dp)
                .clip(CircleShape)
                .padding(24.dp)
        )
        Text(
            text = stringResource(R.string.name),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = stringResource(R.string.email),
            style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ProfilePreview(){
    JetSubmissionTheme {
        Profile()
    }
}