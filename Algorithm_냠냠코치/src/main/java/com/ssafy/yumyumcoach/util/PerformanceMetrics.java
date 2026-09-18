package com.ssafy.yumyumcoach.util;
public record PerformanceMetrics<T>(T result,long elapsedNanos,long comparisons,String algorithm) { public String report(){return "%s | 실행시간 %,dns | 비교 %,d회".formatted(algorithm,elapsedNanos,comparisons);} }
