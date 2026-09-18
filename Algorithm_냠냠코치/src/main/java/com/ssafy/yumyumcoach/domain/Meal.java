package com.ssafy.yumyumcoach.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private long id; private String userId; private LocalDate date; private String mealType; private List<MealEntry> entries = new ArrayList<>(); private String memo;
    public Meal(long id, String userId, LocalDate date, String mealType, List<MealEntry> entries, String memo) { this.id=id; this.userId=userId; this.date=date; this.mealType=mealType; this.entries=new ArrayList<>(entries); this.memo=memo; }
    public long getId(){return id;} public String getUserId(){return userId;} public LocalDate getDate(){return date;} public String getMealType(){return mealType;} public List<MealEntry> getEntries(){return entries;} public String getMemo(){return memo;}
    public void update(LocalDate date,String mealType,List<MealEntry> entries,String memo){this.date=date;this.mealType=mealType;this.entries=new ArrayList<>(entries);this.memo=memo;}
    public double calories(){return entries.stream().mapToDouble(MealEntry::calories).sum();}
    @Override public String toString(){return "#%d %s %s 음식 %d개 %.1fkcal %s".formatted(id,date,mealType,entries.size(),calories(),memo==null?"":memo);}
}
