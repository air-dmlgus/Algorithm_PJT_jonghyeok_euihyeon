package com.ssafy.yumyumcoach.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;

public class JsonStore {
    private final Gson gson=new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,(JsonSerializer<LocalDate>)(s,t,c)->new JsonPrimitive(s.toString())).registerTypeAdapter(LocalDate.class,(JsonDeserializer<LocalDate>)(j,t,c)->LocalDate.parse(j.getAsString())).registerTypeAdapter(LocalDateTime.class,(JsonSerializer<LocalDateTime>)(s,t,c)->new JsonPrimitive(s.toString())).registerTypeAdapter(LocalDateTime.class,(JsonDeserializer<LocalDateTime>)(j,t,c)->LocalDateTime.parse(j.getAsString())).create();
    public <T>T read(Path p,Type type,T fallback){if(!Files.exists(p))return fallback;try(Reader r=Files.newBufferedReader(p,StandardCharsets.UTF_8)){T v=gson.fromJson(r,type);return v==null?fallback:v;}catch(IOException|JsonParseException e){throw new IllegalStateException("JSON 읽기 실패: "+p,e);}}
    public void write(Path p,Object value){try{Files.createDirectories(p.getParent());Path tmp=p.resolveSibling(p.getFileName()+".tmp");try(Writer w=Files.newBufferedWriter(tmp,StandardCharsets.UTF_8)){gson.toJson(value,w);}try{Files.move(tmp,p,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException e){Files.move(tmp,p,StandardCopyOption.REPLACE_EXISTING);}}catch(IOException e){throw new IllegalStateException("JSON 저장 실패: "+p,e);}}
    public static <T> Type listOf(Class<T> c){return TypeToken.getParameterized(java.util.List.class,c).getType();}
}
