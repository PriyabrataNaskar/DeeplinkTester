package com.priyo.deeplinktesterpro.home.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.priyo.deeplinktesterpro.home.ui.utils.openAppViaDeepLink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeepLinkCard(
    text: String,
    onDeleteItem: () -> Unit,
    onCopyItem: (String) -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {
            context.openAppViaDeepLink(text, {})
        }
    ) {
        DeeplinkItem(
            text = text,
            onDeleteItem = { onDeleteItem() },
            onCopyItem = onCopyItem
        )
    }
}