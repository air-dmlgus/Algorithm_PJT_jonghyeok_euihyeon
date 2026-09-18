package com.ssafy.yumyumcoach.util;

import com.ssafy.yumyumcoach.domain.Food;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public final class CsvLoader {
    private CsvLoader(){}
    public static List<Food> loadFoods(Path path){
        try(BufferedReader br=Files.newBufferedReader(path,StandardCharsets.UTF_8)){return parse(br);}
        catch(IOException e){throw new IllegalStateException("음식 CSV를 읽을 수 없습니다: "+path,e);}
    }
    private static List<Food> parse(BufferedReader br)throws IOException{List<Food> out=new ArrayList<>();String line;br.readLine();while((line=br.readLine())!=null){if(line.isBlank())continue;String[] v=line.split(",",-1);out.add(new Food(v[0],v[1],d(v[2]),d(v[3]),d(v[4]),d(v[5]),d(v[6]),d(v[7])));}return out;}
    private static double d(String s){return Double.parseDouble(s.trim());}
}
