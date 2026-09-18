package com.ssafy.yumyumcoach.ai;

import com.ssafy.yumyumcoach.domain.*;
import java.util.List;
public interface GenerativeAiProvider { String name(); boolean external(); String analyzeMeal(User user,Meal meal,AnalysisResult result); String exerciseCoach(User user,List<Meal> recentMeals); }
