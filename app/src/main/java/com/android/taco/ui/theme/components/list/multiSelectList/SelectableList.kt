package com.android.taco.ui.theme.components.list.multiSelectList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.NeutralGray4

@Composable
fun SelectableLazyList(items : ArrayList<String>, selectedItems : ArrayList<String>, onItemClicked : (item : String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(
            items,
            key = { _, item: String ->
                item.hashCode()
            }
        ) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NeutralGray4, RoundedCornerShape(8.dp))
                    .clickable {
                        onItemClicked.invoke(item)
                    }
                    .padding(8.dp)
            ) {
                Text(item, color = BrandPrimary, fontSize = 20.sp)
                if (selectedItems.contains(item)) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(Color.White, CircleShape),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = BrandSecondary,
                    )
                }else{
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(Color.White, CircleShape),
                        imageVector = Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Selected",
                        tint = BrandSecondary,
                    )
                }
            }
        }
    }
}
