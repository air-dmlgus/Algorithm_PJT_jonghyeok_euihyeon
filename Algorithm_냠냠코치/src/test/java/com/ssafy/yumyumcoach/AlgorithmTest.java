package com.ssafy.yumyumcoach;

import com.ssafy.yumyumcoach.domain.Food;
import com.ssafy.yumyumcoach.util.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {
    private final List<Food> foods=List.of(new Food("1","현미밥",210,321,68,6,2,5),new Food("2","비빔밥",500,570,90,18,15,1050),new Food("3","두부",100,110,3,10,6,20));
    @Test void kmpFindsSubstringAndCountsComparisons(){var r=SearchAlgorithms.kmpSearch(foods,Food::name,"밥");assertEquals(2,r.result().size());assertTrue(r.comparisons()>0);}
    @Test void mergeSortOrdersAndCountsComparisons(){var r=SortAlgorithms.mergeSort(foods,Comparator.comparingDouble(Food::calories));assertEquals(List.of("두부","현미밥","비빔밥"),r.result().stream().map(Food::name).toList());assertTrue(r.comparisons()>0);}
}
