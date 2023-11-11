package com.tacomeal.taco.ui.theme.components.dialogBox

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.tacomeal.taco.R
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.TacoTheme
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

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
                modifier = Modifier.padding(24.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                        .wrapContentHeight()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(imageUri == null){
                            Row(modifier = Modifier.padding(8.dp).clickable {
                                launcher.launch("image/*")
                            }) {
                                Icon(imageVector = Icons.Default.Photo,
                                    contentDescription = "image",
                                    tint = BrandSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }else{
                            Image(
                                painter = rememberImagePainter(
                                    data = imageUri,
                                    builder = {
                                        crossfade(false)
                                        placeholder(R.color.imagePlaceholderColor)
                                    }
                                ),
                                contentDescription = "description",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(
                                        shape = RoundedCornerShape(
                                            topStart = 16.dp,
                                            topEnd = 16.dp,
                                            bottomStart = 16.dp,
                                            bottomEnd = 16.dp
                                        )
                                    )
                            )
                        }

                        OutlinedTextField(
                            value = stepDesc,
                            maxLines = 3,
                            onValueChange = { stepDesc = it }, modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            label = { Text("Adım Açıklaması Giriniz") }
                        )
                    }



                    Row(modifier = Modifier.wrapContentHeight()) {
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
                                if(stepDesc.isBlank()){
                                    return@Button
                                }
                                if(imageUri == null){
                                    onSaved(stepDesc, "")
                                }else{
                                    uploadImage(filename = UUID.randomUUID().toString(), imageUri!!, onError = {

                                    }, onSuccess = {
                                        onSaved(stepDesc,it)
                                    })
                                }
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
fun uploadImage(filename : String, fileUri: Uri, onSuccess : (filePath : String) -> Unit, onError : ()-> Unit){
    CoroutineScope(IO).launch {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("StepImages/$filename.jpg")
        val metadata = StorageMetadata()
        val uploadTask = storageRef.putFile(fileUri,metadata)
        uploadTask.addOnFailureListener {
            onError.invoke()
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.path?.let { onSuccess.invoke(it) }
        }
    }

}

@Composable
@Preview
fun NewStepDialogPreview(){
    NewStepDialog({}) {_,_ ->

    }
}