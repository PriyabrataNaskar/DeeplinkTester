package com.priyo.deeplinktesterpro.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyo.deeplinktesterpro.R
import com.priyo.deeplinktesterpro.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeeplinkTesterScreen()
                }
            }
        }
    }
}


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

fun Context.openAppViaDeepLink(
    deeplinkInput: String,
    onSuccess: () -> Unit,
) {
    if (deeplinkInput.isNotEmpty()) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkInput))
            startActivity(intent)
            onSuccess()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_open_deeplink), Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(this, getString(R.string.error_invalid_deeplink), Toast.LENGTH_SHORT).show()
    }
}

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
                    Log.e("tag", "clicked")
                }
                .fillMaxHeight()
                .padding(16.dp)
        )
    }
}

fun Context.copyToClipboard(text: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("deeplink", text)
    clipboard.setPrimaryClip(clip)
    Log.e("tag", "${clip.itemCount} items copied to clipboard")
}

@Preview(showBackground = true)
@Composable
fun DeeplinkTesterPreview() {
    MyApplicationTheme {
        DeeplinkTesterScreen()
    }
}