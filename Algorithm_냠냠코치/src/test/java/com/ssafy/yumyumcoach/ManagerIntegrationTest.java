package com.ssafy.yumyumcoach;

import com.ssafy.yumyumcoach.ai.RuleBasedAiProvider;
import com.ssafy.yumyumcoach.domain.*;
import com.ssafy.yumyumcoach.manager.*;
import com.ssafy.yumyumcoach.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ManagerIntegrationTest {
    @TempDir Path temp;
    @Test void requiredAndAdditionalFeaturesPersistAndRespectOwnership(){
        DataRepository repo=new DataRepository(temp); UserManager users=new UserManager(repo); AuthManager auth=new AuthManager(repo); MealManager meals=new MealManager(repo); ChallengeManager ch=new ChallengeManager(repo); CommunityManager cm=new CommunityManager(repo);
        User u=users.register("tester","pass","테스터",30,"기타",170,65,"없음","균형"); assertTrue(auth.login("tester","pass"));
        Meal meal=meals.create(u.getId(),LocalDate.of(2026,9,18),"점심",List.of(meals.entry("F001",1),meals.entry("F002",1)),"통합 테스트");
        assertTrue(meals.findOwned(meal.getId(),u.getId()).isPresent()); assertTrue(meals.analyze(meal,u).score()>=0); meals.update(meal,meal.getDate(),"저녁",meal.getEntries(),"수정"); assertEquals("저녁",meal.getMealType());
        users.follow(u,"demo"); assertEquals(1,users.following(u).size()); users.unfollow(u,"demo"); assertTrue(users.following(u).isEmpty());
        Challenge c=ch.create("걷기","매일 걷기",LocalDate.now(),LocalDate.now().plusDays(7),"walk.png"); ch.join(c.id(),u.getId()); ch.progress(c.id(),u.getId(),40); assertEquals(40,ch.mine(u.getId()).get(0).getProgress());
        Post p=cm.createPost(u.getId(),"자유","안녕하세요","첫 글"); cm.updatePost(p.getId(),u.getId(),"수정 제목","수정 본문"); Comment comment=cm.createComment(p.getId(),u.getId(),"댓글"); cm.updateComment(comment.getId(),u.getId(),"수정 댓글"); assertEquals(1,cm.comments(p.getId()).size()); cm.deleteComment(comment.getId(),u.getId()); cm.deletePost(p.getId(),u.getId());
        CoachingManager coach=new CoachingManager(new RuleBasedAiProvider(),meals); assertFalse(coach.mealCoach(u,meal).isBlank()); assertFalse(coach.exerciseCoach(u).isBlank());
        meals.delete(meal.getId(),u.getId()); assertTrue(meals.list(u.getId()).isEmpty());
        DataRepository reloaded=new DataRepository(temp); assertTrue(reloaded.user("tester").isPresent());
    }
}
