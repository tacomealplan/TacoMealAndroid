package com.tacomeal.taco.ui.splash.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.tacomeal.taco.R
import com.tacomeal.taco.data.SharedPref
import com.tacomeal.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.tacomeal.taco.ui.theme.NeutralGray2
import com.tacomeal.taco.ui.theme.components.buttons.PrimaryButton
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoarding(toMainView: () -> Unit) {
    LaunchedEffect(Unit){
        com.tacomeal.taco.data.SharedPref.invoke().setSkipOnboarding()
    }

    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                // Replace with your image id
                painterResource(id = R.drawable.background_image),
                contentScale = ContentScale.FillBounds
            )
    ) {
        //OnBoardingData sınıfından OnBoarding ekran sayısını alıyoruz
        val item = getData()
        val state = rememberPagerState(pageCount = item.size)
        //OnBoardingItem'a item resim ve yazıları arayüz elementlerine aktarmasını
        //sağlıyoruz
        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                //Resim, image özelliğine atanıyor
                Image(painter = painterResource(id = item[page].imageR), contentDescription = null)
                //Bold ana başlık Text özelliğine atanıyor

            }
        }
        Box(modifier = Modifier.padding(bottom = 16.dp)){
            Indicators(size = item.size, index = state.currentPage)
        }


        //Ekran sayısını BottomSection compose da kullanarak pager ve scrool işlemi
        BottomSection(item = item[state.currentPage]) {
            if (state.currentPage+1 < item.size)
                scope.launch {
                    state.scrollToPage(page = state.currentPage+1)
                }
            else{
                toMainView()
            }
        }
    }
}

//Pager görselini ekran sayısı kadar çoğaltan compose
@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }

    }

}
//Bottom alanındaki FloatingActionButton ve Pager göstergesini oluşturma
@Composable
fun BottomSection(item: OnBoardingData, onNextClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = Color.White)
    ) {//Pager bölümünü oluşturan compose çağırıyoruz

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
            ) {
            Text(
                text = stringResource(id = item.titleR),
                fontSize = 28.sp,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
            )
            //Açıklama yazısı Text özelliğine atanıyor

            Text(
                text = stringResource(id = item.textR),
                color = NeutralGray2,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            PrimaryButton(text = "İleri") {
                onNextClicked.invoke()
            }

        }


    }
}
//Bir tane Circle şeklinde pager oluşturma
@Composable
fun Indicator(isSelected: Boolean) {
    //pager arasında geçiş yaparkenki animasyonu sağlayan bölüm
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
    )
    //Pager alanı için yükseklik, şekil vb görsel özelliklerini tanımlama
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width = width.value)
            .clip(shape = CircleShape)
            .background(
                if (isSelected) Color.White else Color.LightGray.copy(
                    alpha = 0.5f
                )
            )
    ) {

    }

}
