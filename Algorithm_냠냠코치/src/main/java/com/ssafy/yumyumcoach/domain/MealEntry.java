package com.ssafy.yumyumcoach.domain;

public record MealEntry(String foodId, String foodName, double servings,
                        double calories, double carbohydrate, double protein,
                        double fat, double sodium) {
    public static MealEntry of(Food f, double servings) {
        return new MealEntry(f.id(), f.name(), servings, f.calories() * servings,
                f.carbohydrate() * servings, f.protein() * servings,
                f.fat() * servings, f.sodium() * servings);
    }
}
