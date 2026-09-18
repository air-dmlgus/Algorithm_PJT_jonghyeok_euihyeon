package com.ssafy.yumyumcoach.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public final class SearchAlgorithms {
    private SearchAlgorithms(){}
    public static <T> PerformanceMetrics<List<T>> kmpSearch(List<T> items,Function<T,String> text,String pattern){long start=System.nanoTime(),cmp=0;List<T> found=new ArrayList<>();String p=pattern.toLowerCase(Locale.ROOT);int[] lps=lps(p);for(T item:items){String s=text.apply(item).toLowerCase(Locale.ROOT);int i=0,j=0;while(i<s.length()){cmp++;if(s.charAt(i)==p.charAt(j)){i++;j++;if(j==p.length()){found.add(item);break;}}else if(j>0)j=lps[j-1];else i++;}}return new PerformanceMetrics<>(found,System.nanoTime()-start,cmp,"KMP 부분 문자열 탐색 O(N+M)");}
    private static int[] lps(String p){int[] a=new int[p.length()];for(int i=1,len=0;i<p.length();)if(p.charAt(i)==p.charAt(len))a[i++]=++len;else if(len>0)len=a[len-1];else a[i++]=0;return a;}
}
