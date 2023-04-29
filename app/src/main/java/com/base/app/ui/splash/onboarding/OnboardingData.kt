package com.base.app.ui.splash.onboarding

import com.base.app.R

data class OnBoardingData(val titleR: Int, val textR: Int, val imageR: Int)

fun getData(): List<OnBoardingData> {
    return listOf(
        OnBoardingData(
            titleR = R.string.app_name,
            textR = R.string.app_name,
            imageR = android.R.drawable.star_big_off
        ),
        OnBoardingData(
            titleR = R.string.app_name,
            textR = R.string.app_name,
            imageR = android.R.drawable.stat_sys_phone_call_forward
        ),
        OnBoardingData(
            titleR = R.string.app_name,
            textR = R.string.app_name,
            imageR = android.R.drawable.star_big_off
        ),
    )
}
