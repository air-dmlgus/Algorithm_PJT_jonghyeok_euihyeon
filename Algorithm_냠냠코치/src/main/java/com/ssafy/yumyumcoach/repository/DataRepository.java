package com.ssafy.yumyumcoach.repository;

import com.ssafy.yumyumcoach.domain.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import com.ssafy.yumyumcoach.util.*;

public class DataRepository {
    private final Path dataDir; private final JsonStore json=new JsonStore(); private final Map<String,User> users=new LinkedHashMap<>(); private final Map<String,Food> foods=new LinkedHashMap<>(); private AppState state;
    public DataRepository(Path dataDir){this.dataDir=dataDir;bootstrap();load();}
    private void bootstrap(){try{Files.createDirectories(dataDir);copyIfMissing("data/foods.csv",dataDir.resolve("foods.csv"));copyIfMissing("data/users.json",dataDir.resolve("users.json"));copyIfMissing("data/app-data.json",dataDir.resolve("app-data.json"));}catch(Exception e){throw new IllegalStateException("샘플 데이터 초기화 실패",e);}}
    private void copyIfMissing(String resource,Path target)throws Exception{if(Files.exists(target))return;try(var in=getClass().getClassLoader().getResourceAsStream(resource)){if(in==null)throw new IllegalStateException(resource+" 없음");Files.copy(in,target);}}
    private void load(){for(User u:json.<List<User>>read(dataDir.resolve("users.json"),JsonStore.listOf(User.class),new ArrayList<>()))users.put(u.getId(),u);for(Food f:CsvLoader.loadFoods(dataDir.resolve("foods.csv")))foods.put(f.id(),f);state=json.read(dataDir.resolve("app-data.json"),AppState.class,new AppState());}
    public Collection<User> users(){return users.values();} public Optional<User> user(String id){return Optional.ofNullable(users.get(id));} public void saveUser(User u){users.put(u.getId(),u);save();}
    public Collection<Food> foods(){return foods.values();} public Optional<Food> food(String id){return Optional.ofNullable(foods.get(id));}
    public List<Meal> meals(){return state.meals;} public List<Challenge> challenges(){return state.challenges;} public List<ChallengeParticipation> participations(){return state.participations;} public List<Post> posts(){return state.posts;} public List<Comment> comments(){return state.comments;}
    public long nextMealId(){return next(state.meals.stream().map(Meal::getId).toList());} public long nextChallengeId(){return next(state.challenges.stream().map(Challenge::id).toList());} public long nextPostId(){return next(state.posts.stream().map(Post::getId).toList());} public long nextCommentId(){return next(state.comments.stream().map(Comment::getId).toList());} private long next(List<Long> ids){return ids.stream().mapToLong(Long::longValue).max().orElse(0)+1;}
    public void save(){json.write(dataDir.resolve("users.json"),users.values());json.write(dataDir.resolve("app-data.json"),state);}
    public static <T> boolean removeFirst(List<T> list,Predicate<T> p){return list.removeIf(p);}
}
