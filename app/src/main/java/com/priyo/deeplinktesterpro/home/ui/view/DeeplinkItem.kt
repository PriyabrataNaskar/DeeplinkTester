package com.priyo.deeplinktesterpro.home.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyo.deeplinktesterpro.R

@Composable
fun DeeplinkItem(
    text: String,
    onDeleteItem: () -> Unit,
    onCopyItem: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_content_copy),
            contentDescription = "Icon",
            modifier = Modifier
                .clickable {
                    onCopyItem(text)
                }
                .fillMaxHeight()
                .padding(16.dp)
        )
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            fontSize = 16.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Icon",
            modifier = Modifier
                .clickable {
                    onDeleteItem()
                    Log.e("tag", "clicked") //todo: remove logs
                }
                .fillMaxHeight()
                .padding(16.dp)
        )
    }
}