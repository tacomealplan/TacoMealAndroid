package com.android.taco.model

data class PlanDay(
    var breakfast: List<String>,
    var dinner: List<String>,
    var lunch: List<String>

){

    fun addRecipeToDailyPlan(meal : Meal, recipe : Recipe){
        when(meal) {
            Meal.Breakfast -> {
                breakfast = breakfast.plus(recipe.id)
            }
            Meal.Lunch -> {
                lunch = lunch.plus(recipe.id)
            }
            Meal.Dinner -> {
                dinner = dinner.plus(recipe.id)
            }
        }
    }

    fun getRecipesByMeal(meal : Meal) : List<String>{
        return when(meal){
            Meal.Breakfast -> {
                breakfast
            }

            Meal.Dinner -> {
                dinner
            }

            Meal.Lunch -> {
                lunch
            }
        }
    }
}