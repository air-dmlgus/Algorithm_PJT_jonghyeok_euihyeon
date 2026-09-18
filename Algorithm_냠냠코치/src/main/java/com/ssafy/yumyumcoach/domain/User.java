package com.ssafy.yumyumcoach.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {
    private String id; private String password; private String name; private int age; private String gender; private double heightCm; private double weightKg; private String disease; private String goal; private boolean active=true; private Set<String> following=new LinkedHashSet<>();
    public User(String id,String password,String name,int age,String gender,double heightCm,double weightKg,String disease,String goal){this.id=id;this.password=password;this.name=name;this.age=age;this.gender=gender;this.heightCm=heightCm;this.weightKg=weightKg;this.disease=disease;this.goal=goal;}
    public String getId(){return id;} public String getPassword(){return password;} public String getName(){return name;} public int getAge(){return age;} public String getGender(){return gender;} public double getHeightCm(){return heightCm;} public double getWeightKg(){return weightKg;} public String getDisease(){return disease;} public String getGoal(){return goal;} public boolean isActive(){return active;} public Set<String> getFollowing(){return following;}
    public void update(String name,int age,String gender,double heightCm,double weightKg,String disease,String goal){this.name=name;this.age=age;this.gender=gender;this.heightCm=heightCm;this.weightKg=weightKg;this.disease=disease;this.goal=goal;}
    public void changePassword(String password){this.password=password;} public void deactivate(){active=false;following.clear();}
    public double bmi(){return weightKg/Math.pow(heightCm/100.0,2);} public double targetCalories(){return Math.max(1200,10*weightKg+6.25*heightCm-5*age+("남".equals(gender)?5:-161));}
    @Override public String toString(){return "%s(%s) %d세 %.0fcm/%.1fkg BMI %.1f 질환:%s 목표:%s 상태:%s".formatted(name,id,age,heightCm,weightKg,bmi(),disease,goal,active?"활성":"비활성");}
}
