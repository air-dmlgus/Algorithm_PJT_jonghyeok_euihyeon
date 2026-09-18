package com.ssafy.yumyumcoach.ai;

import com.ssafy.yumyumcoach.domain.*;
import java.util.List;
/** 외부 생성형 AI 연동 지점. 실제 구현은 이 클래스를 상속하거나 동일 인터페이스를 구현한다. */
public abstract class HttpAiProvider implements GenerativeAiProvider { protected final String apiKey; protected HttpAiProvider(String apiKey){if(apiKey==null||apiKey.isBlank())throw new IllegalArgumentException("API 키가 필요합니다.");this.apiKey=apiKey;} public boolean external(){return true;} protected abstract String complete(String systemPrompt,String userPrompt); public String analyzeMeal(User u,Meal m,AnalysisResult r){return complete("당신은 영양 코치입니다. 진단하지 말고 일반적 조언만 하세요.","사용자="+u+" 식단="+m+" 수치="+r);} public String exerciseCoach(User u,List<Meal> meals){return complete("당신은 안전을 우선하는 운동 코치입니다.","사용자="+u+" 최근 식단 수="+meals.size());} }
