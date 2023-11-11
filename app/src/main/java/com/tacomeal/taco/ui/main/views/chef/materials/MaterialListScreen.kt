package com.tacomeal.taco.ui.main.views.chef.materials

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.R
import com.tacomeal.taco.model.Material
import com.tacomeal.taco.model.UserCart
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.TacoTheme
import com.tacomeal.taco.ui.theme.White
import com.tacomeal.taco.ui.theme.components.buttons.PrimaryButton
import com.tacomeal.taco.ui.theme.components.buttons.SecondaryButton

@Composable
fun MaterialListScreen(materialList: ArrayList<Material>,
                       materialsInCartList : List<UserCart>,
                       onMaterialAddedToCart : (material : Material) -> Unit
){
    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = Color.White)
        ) {
            Text(
                text = "Malzemeler",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${materialList.size} Malzeme",
                    color = Color(0xff748189),
                    style = TextStyle(
                        fontSize = 16.sp))

                /*Text(
                    text = "Hepsini Sepete Ekle",
                    color = Color(0xffff8c00),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable {

                    }
                )*/
            }
            /*LazyColumn {
                items(materialList.size) { index ->
                    MaterialRow(materialList[index]){}

                }
            }*/
            //Text(text = "Test")
            /*MaterialRow(Material("1","test")){}
            MaterialRow(material = materialList.first()) {
                
            }*/
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier//.verticalScroll(state = rememberScrollState()).fillMaxSize()
            ) {
                materialList.forEach {material ->
                    MaterialRow(material = material,
                        isInCart = materialsInCartList.any { it.materialId ==  material.id}
                    ){
                        onMaterialAddedToCart.invoke(material)
                    }
                }

            }
        }
    }
}
@Composable
fun MaterialRow(material: Material,isInCart: Boolean = false, onAdded : () -> Unit) {
    val isTitle = material.name.startsWith("**") &&
            material.name.endsWith("**")
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = if(isTitle) material.name.removeSurrounding("**") else material.name,
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = if(isTitle) FontWeight.Bold else FontWeight.Normal),
                modifier = Modifier.fillMaxWidth(0.9f))
            if(!isTitle){
                if(isInCart){
                    Image(
                        painter = painterResource(id = R.drawable.tick),
                        contentDescription = "Added",
                        colorFilter = ColorFilter.tint(Color(0xffff8c00)),
                        modifier = Modifier
                            .size(size = 28.dp)
                    )
                }else{
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Added",
                        colorFilter = ColorFilter.tint(Color(0xffff8c00)),
                        modifier = Modifier
                            .size(size = 28.dp)
                            .clickable {
                                onAdded.invoke()
                            }
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun MaterialListScreenPreview(){
    MaterialListScreen(
        arrayListOf<Material>(Material("1", "Test"),
        Material("1", "**Test**"),
        Material("1", "Test Deneme Deneme Deneme Dneeme Dneeme "),
        Material("1213", "Deneme")),
        arrayListOf(UserCart("0","","",isChecked = false,"1213","","",""))
    ){

    }
}