package com.tacomeal.taco.ui.theme.components.dialogBox

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.TacoTheme

@Composable
fun DeleteComfirmDialog(message : String,onDismiss:() -> Unit, onConfirmed : () -> Unit) {

    TacoTheme() {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                //shape = MaterialTheme.shapes.medium,
                shape = RoundedCornerShape(10.dp),
                // modifier = modifier.size(280.dp, 240.dp)
                modifier = Modifier.padding(24.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier.background(Color.White)
                ) {

                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        color = BrandPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        OutlinedButton(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Vazgeç", color = BrandSecondary)
                        }


                        Button(
                            onClick = {
                                onConfirmed.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = BrandSecondary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Sil")
                        }
                    }
                }
            }
        }
    }


}

@Composable
@Preview
fun DeleteComfirmDialogPreview(){
    DeleteComfirmDialog(message = "Planı silmek istediğinize emin misiniz?",{}) {

    }
}