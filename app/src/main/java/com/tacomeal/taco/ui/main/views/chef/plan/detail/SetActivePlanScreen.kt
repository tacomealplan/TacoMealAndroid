package com.tacomeal.taco.ui.main.views.chef.plan.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.TacoTheme
import com.tacomeal.taco.ui.theme.components.buttons.SecondaryButton

@Composable
fun SetActivePlanScreen(planSelected : (week : Int) -> Unit){
    val radioOptions = listOf("Bu hafta", "Gelecek hafta")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }
    TacoTheme {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(text = "Lütfen planın uygulanacağı haftayı seçiniz",
                color = BrandPrimary,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SimpleRadioButtonComponent(items = radioOptions, selectedOption.value){
                selectedOption.value = it
            }
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(text = "Planı Seç") {
                planSelected.invoke(if(selectedOption.value == radioOptions[0]) 0 else 1)
            }
        }
    }
}

@Composable
fun SimpleRadioButtonComponent(items : List<String>, selectedOption : String, onOptionSelected : (item : String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            items.forEach { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected.invoke(text) }
                        )
                        .padding(horizontal = 16.dp)
                ) {

                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = 8.dp),
                        onClick = {
                            onOptionSelected.invoke(text)
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SetActivePlanScreenPreview(){
    SetActivePlanScreen(){}
}