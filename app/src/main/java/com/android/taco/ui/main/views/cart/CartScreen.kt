package com.android.taco.ui.main.views.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.R
import com.android.taco.ui.theme.Black
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.White
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.bars.TitleTopBar

@Composable
fun CartScreen() {
    TacoTheme() {
        Scaffold(topBar = {
            TitleTopBar(title = "Alışveriş Listem")
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = BrandSecondary,
                contentColor = White
            ) {
                Icon(Icons.Filled.Add,"")
            }
        }) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(it)
                    .padding(24.dp)
                    .fillMaxSize()
                    .background(White)
            ) {
                CartItem("Ekmek", false){

                }
                CartItem("Ekmek", false){

                }
                CartItem("Ekmek", true){

                }
                CartItem("Ekmek", false){

                }

            }
        }
    }

}

@Composable
private fun CartItem(cartItem : String, isSelected : Boolean, onChecked: () -> Unit){
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        CircleCheckbox(selected = isSelected) {
            onChecked.invoke()
        }
        Text(text = cartItem)
    }
}

@Composable
fun CircleCheckbox(selected: Boolean, enabled: Boolean = true, onChecked: () -> Unit) {
    val imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val tint = if (selected) BrandSecondary.copy(alpha = 0.8f) else White.copy(alpha = 0.8f)
    val background = if (selected) White else Color.Transparent

    IconButton(onClick = { onChecked() },
        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
        enabled = enabled) {

        Icon(imageVector = imageVector, tint = tint,
            modifier = Modifier
                .background(background, shape = CircleShape)
                .border(
                    width = 1.dp,
                    Black, CircleShape
                ),
            contentDescription = "checkbox")
    }
}

@Preview
@Composable

fun CartScreenPreview(){
    CartScreen()
}