package com.ssafy.yumyumcoach.manager;

import com.ssafy.yumyumcoach.domain.*;
import com.ssafy.yumyumcoach.repository.DataRepository;
import com.ssafy.yumyumcoach.util.*;
import java.time.LocalDate;
import java.util.*;

public class MealManager {
    private final DataRepository repo; public MealManager(DataRepository r){repo=r;}
    public Collection<Food> foods(){return repo.foods();}
    public PerformanceMetrics<List<Food>> searchFoods(String keyword){if(keyword.isBlank())return new PerformanceMetrics<>(new ArrayList<>(repo.foods()),0,0,"전체 조회");return SearchAlgorithms.kmpSearch(new ArrayList<>(repo.foods()),Food::name,keyword);}
    public Meal create(String userId,LocalDate date,String type,List<MealEntry> entries,String memo){if(entries.isEmpty())throw new IllegalArgumentException("음식을 하나 이상 선택하세요.");Meal m=new Meal(repo.nextMealId(),userId,date,type,entries,memo);repo.meals().add(m);repo.save();return m;}
    public List<Meal> list(String userId){return repo.meals().stream().filter(m->m.getUserId().equals(userId)).toList();}
    public Optional<Meal> findOwned(long id,String userId){return repo.meals().stream().filter(m->m.getId()==id&&m.getUserId().equals(userId)).findFirst();}
    public void update(Meal m,LocalDate d,String type,List<MealEntry> entries,String memo){if(entries.isEmpty())throw new IllegalArgumentException("음식을 하나 이상 선택하세요.");m.update(d,type,entries,memo);repo.save();}
    public void delete(long id,String userId){if(!repo.meals().removeIf(m->m.getId()==id&&m.getUserId().equals(userId)))throw new IllegalArgumentException("식단을 찾을 수 없습니다.");repo.save();}
    public MealEntry entry(String foodId,double servings){Food f=repo.food(foodId).orElseThrow(()->new IllegalArgumentException("음식 ID가 없습니다."));return MealEntry.of(f,servings);}
    public AnalysisResult analyze(Meal m,User u){double cal=0,carb=0,protein=0,fat=0,sodium=0;for(MealEntry e:m.getEntries()){cal+=e.calories();carb+=e.carbohydrate();protein+=e.protein();fat+=e.fat();sodium+=e.sodium();}double mealTarget=u.targetCalories()/3.0;int score=100;score-=Math.min(35,(int)(Math.abs(cal-mealTarget)/mealTarget*50));if(protein<20)score-=15;if(sodium>700)score-=Math.min(25,(int)((sodium-700)/50));double totalMacro=Math.max(1,carb*4+protein*4+fat*9),fatRatio=fat*9/totalMacro;if(fatRatio>.35)score-=10;score=Math.max(0,score);String grade=score>=85?"A":score>=70?"B":score>=55?"C":"D";StringBuilder advice=new StringBuilder();if(protein<20)advice.append("단백질을 보충하세요. ");if(sodium>700)advice.append("나트륨이 높으니 국물·가공식품을 줄이세요. ");if(cal>mealTarget*1.2)advice.append("목표 대비 열량이 높습니다. ");if(advice.isEmpty())advice.append("영양 균형이 양호합니다.");return new AnalysisResult(cal,carb,protein,fat,sodium,score,grade,advice.toString().trim());}
    public PerformanceMetrics<List<Meal>> sortByCalories(String userId){return SortAlgorithms.mergeSort(list(userId),Comparator.comparingDouble(Meal::calories));}
    public PerformanceMetrics<List<Food>> recommend(User u,int count){long start=System.nanoTime(),cmp=0;PriorityQueue<FoodScore>q=new PriorityQueue<>(Comparator.comparingDouble(FoodScore::score));for(Food f:repo.foods()){double s=f.protein()*3-f.sodium()/100.0-Math.abs(f.calories()-250)/30;if(u.getDisease().contains("고혈압"))s-=f.sodium()/50;cmp++;q.offer(new FoodScore(f,s));if(q.size()>count)q.poll();}List<Food>r=new ArrayList<>();while(!q.isEmpty())r.add(q.poll().food());Collections.reverse(r);return new PerformanceMetrics<>(r,System.nanoTime()-start,cmp,"우선순위 큐 Top-K 추천 O(n log k)");}
    private record FoodScore(Food food,double score){}
}
