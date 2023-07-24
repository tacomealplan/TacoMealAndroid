package com.android.taco.ui.theme.components.dialogBox

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoAlbum
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
fun NewStepDialog(onDismiss:() -> Unit, onSaved : (stepDesc : String, photoUrl : String) -> Unit) {
    var stepDesc by remember {
        mutableStateOf("")
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
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
                    Modifier
                        .background(Color.White)
                        .wrapContentHeight()
                ) {

                    OutlinedTextField(
                        value = stepDesc,
                        maxLines = 3,
                        onValueChange = { stepDesc = it }, modifier = Modifier.padding(8.dp),
                        label = { Text("Adım Giriniz") }
                    )

                    Row(modifier = Modifier.padding(8.dp).clickable {
                        launcher.launch("image/*")
                    }) {
                        Icon(imageVector = Icons.Default.Photo,
                            contentDescription = "image",
                            tint = Color.Blue,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Fotoğraf eklemek için tıklayınız", color = Color.Blue)
                    }

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
                                onSaved(stepDesc, "")
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
fun NewStepDialogPreview(){
    NewStepDialog({}) {_,_ ->

    }
}