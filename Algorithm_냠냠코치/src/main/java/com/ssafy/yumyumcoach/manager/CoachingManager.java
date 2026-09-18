package com.ssafy.yumyumcoach.manager;

import com.ssafy.yumyumcoach.ai.GenerativeAiProvider;
import com.ssafy.yumyumcoach.domain.*;
public class CoachingManager { private final GenerativeAiProvider ai; private final MealManager meals; public CoachingManager(GenerativeAiProvider ai,MealManager meals){this.ai=ai;this.meals=meals;} public String mealCoach(User u,Meal m){return ai.analyzeMeal(u,m,meals.analyze(m,u));} public String exerciseCoach(User u){return ai.exerciseCoach(u,meals.list(u.getId()));} public String providerInfo(){return ai.name()+(ai.external()?" (외부 AI)":" (API 키 불필요 fallback)");} }
