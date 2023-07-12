package com.android.taco.model

import java.util.UUID

data class UserPlan(
    val planId: String,
    val userId: String,
    var weekOfYear: String,
    var isActive: Boolean,
    val createDate: String,
) {
    companion object{

        fun newInstance(data: MutableMap<String, Any>): UserPlan{
            val userPlan = UserPlan(
                planId = data["PlanId"].toString(),
                userId = data["UserId"].toString(),
                createDate = data["CreateDate"].toString(),
                weekOfYear = data["WeekOfYear"].toString(),
                isActive = data["isActive"].toString().toBoolean()
            )
            return userPlan
        }
    }
}