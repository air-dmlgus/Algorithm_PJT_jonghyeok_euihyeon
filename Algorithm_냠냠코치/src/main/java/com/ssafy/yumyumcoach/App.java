package com.ssafy.yumyumcoach;

import com.ssafy.yumyumcoach.ai.RuleBasedAiProvider;
import com.ssafy.yumyumcoach.manager.*;
import com.ssafy.yumyumcoach.repository.DataRepository;
import com.ssafy.yumyumcoach.ui.MenuUI;
import java.nio.file.Path;
public class App { public static void main(String[] args){Path data=Path.of(System.getProperty("yumyum.data.dir","data"));DataRepository repo=new DataRepository(data);MealManager meal=new MealManager(repo);new MenuUI(new AuthManager(repo),new UserManager(repo),meal,new ChallengeManager(repo),new CommunityManager(repo),new CoachingManager(new RuleBasedAiProvider(),meal)).run();} }
