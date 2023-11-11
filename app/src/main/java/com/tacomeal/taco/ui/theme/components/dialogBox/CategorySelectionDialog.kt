package com.tacomeal.taco.ui.theme.components.dialogBox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tacomeal.taco.ui.theme.TacoTheme
import com.tacomeal.taco.ui.theme.components.editTexts.SearchTextField
import com.tacomeal.taco.ui.theme.components.list.multiSelectList.SelectableLazyList

@Composable
fun CategorySelectionDialog(items : ArrayList<String>,
                            selectedItems : ArrayList<String>,
                            onItemClicked : (item : String) -> Unit,
                            onDismiss:() -> Unit,
                            onSaved : (material : String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    TacoTheme() {
        Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { onDismiss() }) {
            Card(
                //shape = MaterialTheme.shapes.medium,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                elevation = 8.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Kategori Seçiniz", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.fillMaxWidth(0.9f), textAlign = TextAlign.Center)
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "check", modifier = Modifier.clickable {
                            onDismiss()
                        })
                    }

                    SearchTextField(value = searchText, placeholder = "Ara", modifier = Modifier.fillMaxWidth(), onValueChange = {value ->
                        searchText = value
                    })
                    SelectableLazyList(items = items.filter { it.lowercase().contains(searchText.lowercase()) } as ArrayList<String>, selectedItems = selectedItems, onItemClicked = onItemClicked)
                }
            }
        }
    }


}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CategorySelectionDialogPreview(){
    CategorySelectionDialog(items = arrayListOf("item1", "item2"), selectedItems = arrayListOf("item1"),{},{}) {

    }
}