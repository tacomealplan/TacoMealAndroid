package com.android.taco.ui.splash.onboarding

import com.android.taco.R

data class OnBoardingData(val titleR: Int, val textR: Int, val imageR: Int)

fun getData(): List<OnBoardingData> {
    return listOf(
        OnBoardingData(
            titleR = R.string.onboarding_title_1,
            textR = R.string.onboarding_text_1,
            imageR = R.drawable.onboarding_1_image
        ),
        OnBoardingData(
            titleR = R.string.onboarding_title_2,
            textR = R.string.onboarding_text_2,
            imageR = R.drawable.onboarding_2_image
        ),
        OnBoardingData(
            titleR = R.string.onboarding_title_3,
            textR = R.string.onboarding_text_3,
            imageR = R.drawable.onboarding_3_image
        ),
    )
}
