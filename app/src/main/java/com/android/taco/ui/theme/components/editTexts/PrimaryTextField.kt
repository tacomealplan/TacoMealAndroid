package com.android.taco.ui.theme.components.editTexts

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.NeutralGray2

@Composable
fun PrimaryTextField(value : String,
                     label : String = "",
                     placeholder: String,
                     enabled : Boolean = true,
                     singleLine : Boolean = false,
                     minLines : Int = 1,
                     maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
                     modifier: Modifier = Modifier,
                     onValueChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(8.dp)
    ) {
        if(label.isNotBlank()){
            Text(
                text = label,
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium)

            )
            Spacer(
                modifier = Modifier
                    .height(height = 12.dp))
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(16.dp),
            enabled = enabled,
            placeholder = { Text(placeholder) },
            minLines = minLines,
            maxLines = maxLines,
            singleLine = singleLine,
            textStyle = TextStyle(
                fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = BrandPrimary,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = NeutralGray2, shape = RoundedCornerShape(16.dp)))
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryTextFieldPreview(){
    PrimaryTextField(value = "value", label = "E-Posta", placeholder = "E-Posta adresinizi girin"){

    }
}