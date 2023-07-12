package com.android.taco.ui.main.views.chef.recipe.edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taco.R
import com.android.taco.model.Material
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.NeutralGray2
import com.android.taco.ui.theme.NeutralGray4
import com.android.taco.ui.theme.components.dialogBox.NewMaterialDialog
import com.android.taco.ui.theme.components.dialogBox.NewTitleDialog
import com.android.taco.ui.theme.components.list.dragAndDropList.DragDropColumn
import com.android.taco.ui.theme.components.swipe.SwipeBackground
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MaterialsEditListScreen(viewModel: EditRecipeViewModel){
    var openNewMaterialDialog by remember { mutableStateOf(false) }
    var openNewTitleDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.materialsState.collectAsState()
    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Malzemeler",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )

            Text(
                text = "${uiState.value.count()} Malzeme",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 16.sp))
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.outline_subtitles_24),
                    contentDescription = "Added",
                    modifier = Modifier
                        .background(color = BrandSecondary, shape = CircleShape)
                        .padding(4.dp)
                        .size(size = 20.dp)
                        .clickable {
                            openNewTitleDialog = true
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Added",
                    colorFilter = ColorFilter.tint(BrandSecondary),
                    modifier = Modifier
                        .size(size = 28.dp)
                        .clickable {
                            openNewMaterialDialog = true
                        }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = NeutralGray4)
                .padding(horizontal = 12.dp, vertical = 12.dp)) {
                DragDropColumn(
                    items = uiState.value,
                    onSwap = viewModel::swapMaterials
                ) { material ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            viewModel.removeMaterial(material)
                            true
                        }
                    )

                    SwipeToDismiss(state = dismissState,directions= setOf(
                        DismissDirection.EndToStart
                    ), background = {
                        SwipeBackground(dismissState)
                    }) {
                        MaterialRow(material)
                    }

                }
            }
            if(openNewMaterialDialog){
                NewMaterialDialog(onDismiss = { openNewMaterialDialog = false }, onSaved = {material ->
                    viewModel.addMaterial(Material(UUID.randomUUID().toString(),material))
                    openNewMaterialDialog = false
                })
            }
            if(openNewTitleDialog){
                NewTitleDialog(onDismiss = { openNewTitleDialog = false }, onSaved = {title ->
                    viewModel.addMaterial(Material(UUID.randomUUID().toString(),"**${title}**"))
                    openNewTitleDialog = false
                })
            }
        }


    }
}

@Composable
private fun MaterialRow(item: Material) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {

            },
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_format_line_spacing_24),
            contentDescription = "Added",
            colorFilter = ColorFilter.tint(NeutralGray2),
            modifier = Modifier
                .size(size = 28.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.name,
                color = BrandPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
            Divider()
        }

    }
}

@Preview
@Composable
fun MaterialsEditListScreenPreview(){
    MaterialsEditListScreen(viewModel = viewModel())
}
