package com.ssafy.yumyumcoach.repository;

import com.ssafy.yumyumcoach.domain.*;
import java.util.ArrayList;
import java.util.List;
public class AppState { public List<Meal> meals=new ArrayList<>(); public List<Challenge> challenges=new ArrayList<>(); public List<ChallengeParticipation> participations=new ArrayList<>(); public List<Post> posts=new ArrayList<>(); public List<Comment> comments=new ArrayList<>(); }
