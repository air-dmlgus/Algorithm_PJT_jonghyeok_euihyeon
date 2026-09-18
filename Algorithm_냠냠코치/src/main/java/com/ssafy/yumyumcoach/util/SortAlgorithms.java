package com.ssafy.yumyumcoach.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
public final class SortAlgorithms { private SortAlgorithms(){} public static <T> PerformanceMetrics<List<T>> mergeSort(List<T> source,Comparator<T> c){long s=System.nanoTime();AtomicLong n=new AtomicLong();List<T> r=sort(new ArrayList<>(source),c,n);return new PerformanceMetrics<>(r,System.nanoTime()-s,n.get(),"병합 정렬 O(n log n)");} private static <T> List<T> sort(List<T>a,Comparator<T>c,AtomicLong n){if(a.size()<2)return a;int m=a.size()/2;List<T>l=sort(new ArrayList<>(a.subList(0,m)),c,n),r=sort(new ArrayList<>(a.subList(m,a.size())),c,n),o=new ArrayList<>(a.size());int i=0,j=0;while(i<l.size()&&j<r.size()){n.incrementAndGet();if(c.compare(l.get(i),r.get(j))<=0)o.add(l.get(i++));else o.add(r.get(j++));}o.addAll(l.subList(i,l.size()));o.addAll(r.subList(j,r.size()));return o;} }
