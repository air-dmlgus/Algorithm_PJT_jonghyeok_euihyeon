package com.ssafy.yumyumcoach.domain;

public record Food(String id, String name, double servingGram, double calories,
                   double carbohydrate, double protein, double fat, double sodium) {
    public String summary() {
        return "%s(%s) %.0fg | %.1fkcal 탄%.1f 단%.1f 지%.1f 나%.0fmg".formatted(
                name, id, servingGram, calories, carbohydrate, protein, fat, sodium);
    }
}
