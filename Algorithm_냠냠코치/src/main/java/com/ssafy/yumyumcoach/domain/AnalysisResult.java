package com.ssafy.yumyumcoach.domain;
public record AnalysisResult(double calories,double carbohydrate,double protein,double fat,double sodium,int score,String grade,String advice) { @Override public String toString(){return "총 %.1fkcal | 탄 %.1fg 단 %.1fg 지 %.1fg 나트륨 %.0fmg | 점수 %d(%s)\n%s".formatted(calories,carbohydrate,protein,fat,sodium,score,grade,advice);} }
