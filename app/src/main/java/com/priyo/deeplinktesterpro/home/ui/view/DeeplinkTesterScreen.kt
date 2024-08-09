package com.priyo.deeplinktesterpro.home.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyo.deeplinktesterpro.R
import com.priyo.deeplinktesterpro.home.ui.utils.copyToClipboard
import com.priyo.deeplinktesterpro.home.ui.utils.openAppViaDeepLink
import com.priyo.deeplinktesterpro.ui.theme.MyApplicationTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DeeplinkTesterScreen() {
    var deeplink by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is HomeSideEffect.DeepLinkSaved -> {}
            is HomeSideEffect.CopyDeepLinkToClipBoard -> context.copyToClipboard(it.deeplink)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .weight(1f)
        ) {
            itemsIndexed(state.deepLinks) { index, item ->
                DeepLinkCard(
                    text = item.deepLink,
                    onDeleteItem = {
                        viewModel.deleteDeepLink(item, index)
                    },
                    onCopyItem = {
                        viewModel.copyDeepLinkToClipBoard(it)
                    })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        OutlinedTextField(
            value = deeplink,
            onValueChange = { deeplink = it },
            label = {
                Text(
                    text = stringResource(R.string.enter_deeplink_url),
                    modifier = Modifier
                )
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier.clickable { deeplink = TextFieldValue("") }
                )
            }
        )
        Button(
            onClick = {
                context.openAppViaDeepLink(
                    deeplinkInput = deeplink.text,
                    onSuccess = {
                        viewModel.saveDeepLink(deeplink.text)
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            content = { Text(stringResource(R.string.open_deeplink)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeeplinkTesterPreview() {
    MyApplicationTheme {
        DeeplinkTesterScreen()
    }
}