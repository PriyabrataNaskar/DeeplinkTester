package com.priyo.deeplinktesterpro.home.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyo.deeplinktesterpro.R
import com.priyo.deeplinktesterpro.ui.theme.MyApplicationTheme

@Composable
fun DeeplinkItem(
    text: String,
    onDeleteItem: () -> Unit,
    onCopyItem: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_content_copy),
            contentDescription = "Icon",
            modifier = Modifier
                .clickable {
                    onCopyItem(text)
                }
                .fillMaxHeight()
                .padding(vertical = 16.dp)
                .padding(horizontal = 8.dp)
        )
        Text(
            text = text,
            modifier = Modifier.weight(1f).padding(vertical = 8.dp),
            fontSize = 16.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis
        )
        Icon(imageVector = Icons.Default.Clear,
            contentDescription = "Icon",
            modifier = Modifier
                .clickable {
                    onDeleteItem()
                    Log.e("tag", "clicked") //todo: remove logs
                }
                .fillMaxHeight()
                .padding(vertical = 16.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeeplinkItemPreview() {
    MyApplicationTheme {
        DeeplinkItem(
            text = "https://www.google.com",
            onDeleteItem = {},
            onCopyItem = {}
        )
    }
}