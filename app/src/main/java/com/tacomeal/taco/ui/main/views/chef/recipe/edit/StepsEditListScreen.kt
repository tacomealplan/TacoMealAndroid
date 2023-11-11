package com.tacomeal.taco.ui.main.views.chef.recipe.edit

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.R
import com.tacomeal.taco.model.Step
import com.tacomeal.taco.ui.main.views.chef.steps.StepImage
import com.tacomeal.taco.ui.main.views.chef.steps.StepNumber
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.NeutralGray2
import com.tacomeal.taco.ui.theme.NeutralGray4
import com.tacomeal.taco.ui.theme.White
import com.tacomeal.taco.ui.theme.components.dialogBox.NewStepDialog
import com.tacomeal.taco.ui.theme.components.dialogBox.NewTitleDialog
import com.tacomeal.taco.ui.theme.components.list.dragAndDropList.DragDropColumn
import com.tacomeal.taco.ui.theme.components.swipe.SwipeBackground
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StepsEditListScreen(viewModel: EditRecipeViewModel){
    var openNewStepDialog by remember { mutableStateOf(false) }
    var openNewTitleDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.stepsState.collectAsState()
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
                text = "Adımlar",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )

            Text(
                text = "${uiState.value.count()} Adım",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 16.sp)
            )
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
                            openNewStepDialog = true
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
                    onSwap = viewModel::swapSteps
                ) { step ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            viewModel.removeStep(step)
                            true
                        }
                    )

                    SwipeToDismiss(state = dismissState,directions= setOf(
                        DismissDirection.EndToStart
                    ), background = {
                        SwipeBackground(dismissState)
                    }) {
                        StepRow(step)
                    }

                }
            }
            if(openNewStepDialog){
                NewStepDialog(onDismiss = { openNewStepDialog = false }, onSaved = {stepDesc, photoUrl ->
                    viewModel.addStep(Step(description = stepDesc, index = 1, id = UUID.randomUUID().toString(), photoLink = photoUrl))
                    openNewStepDialog = false
                })
            }
            if(openNewTitleDialog){
                NewTitleDialog(onDismiss = { openNewTitleDialog = false }, onSaved = {title ->
                    viewModel.addStep(Step(description = title, index = 0, id = UUID.randomUUID().toString(), photoLink = ""))
                    openNewTitleDialog = false
                })
            }
        }


    }
}

@Composable
private fun StepRow(step: Step) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_format_line_spacing_24),
                contentDescription = "Added",
                colorFilter = ColorFilter.tint(NeutralGray2),
                modifier = Modifier
                    .size(size = 28.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            StepNumber(step.index)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = step.description,
                color = Color(0xff48525f),
                style = TextStyle(
                    fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth())
        }
        if(step.photoLink.isNotBlank()){
            StepImage(step.photoLink){}
        }
    }
}