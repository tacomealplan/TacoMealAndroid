package com.android.taco.ui.theme.components.dialogBox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme

@Composable
fun NewTitleDialog(onDismiss:() -> Unit, onSaved : (title : String) -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    TacoTheme() {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                //shape = MaterialTheme.shapes.medium,
                shape = RoundedCornerShape(10.dp),
                // modifier = modifier.size(280.dp, 240.dp)
                modifier = Modifier.padding(8.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier.background(Color.White)
                ) {

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it }, modifier = Modifier.padding(8.dp),
                        label = { Text("Başlık Giriniz") }
                    )

                    Row {
                        OutlinedButton(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "İptal", color = BrandSecondary)
                        }


                        Button(
                            onClick = {
                                onSaved(title)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = BrandSecondary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Kaydet")
                        }
                    }


                }
            }
        }
    }


}

@Composable
@Preview
fun NewTitleDialogPreview(){
    NewTitleDialog({}) {

    }
}