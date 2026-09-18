package com.ssafy.yumyumcoach.manager;

import com.ssafy.yumyumcoach.domain.User;
import com.ssafy.yumyumcoach.repository.DataRepository;
import java.util.Optional;
public class AuthManager { private final DataRepository repo; private User current; public AuthManager(DataRepository r){repo=r;} public boolean login(String id,String pw){Optional<User>u=repo.user(id);if(u.isPresent()&&u.get().isActive()&&u.get().getPassword().equals(pw)){current=u.get();return true;}return false;} public void logout(){current=null;} public boolean loggedIn(){return current!=null;} public User require(){if(current==null)throw new IllegalStateException("로그인이 필요합니다.");return current;} }
