package com.ssafy.yumyumcoach.domain;

import java.time.LocalDateTime;
public class Comment { private long id; private long postId; private String authorId; private String content; private LocalDateTime createdAt=LocalDateTime.now(); public Comment(long id,long p,String a,String c){this.id=id;postId=p;authorId=a;content=c;} public long getId(){return id;} public long getPostId(){return postId;} public String getAuthorId(){return authorId;} public String getContent(){return content;} public LocalDateTime getCreatedAt(){return createdAt;} public void update(String c){content=c;} @Override public String toString(){return "댓글#%d %s: %s".formatted(id,authorId,content);}}
