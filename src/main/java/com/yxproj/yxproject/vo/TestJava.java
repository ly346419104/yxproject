package com.yxproj.yxproject.vo;

import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestJava {
    private static int binarySearch(int[] array, int target) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                index = i;
                break;
            } else {

            }
        }
        return index;
    }

    private static int findMode(int[] array) {
        //次数
       AtomicInteger maxCount= new AtomicInteger();
       //
       AtomicInteger maxNum= new AtomicInteger();
        HashMap<@Nullable Integer, @Nullable Integer> intMap = Maps.newHashMap();
        for (int i = 0; i < array.length; i++) {
            if (intMap.get(array[i])!=null&&intMap.get(array[i])>0){
                intMap.put(array[i],intMap.get(array[i])+1);
            }else {

                intMap.put(array[i],1);
            }
        }
        intMap.forEach((key,value)->{

            if (maxCount.get()<value){
                maxCount.set(value);
                maxNum.set(key);
            }
        });
        return maxNum.get();
    }

    public static void main(String[] args) {

        int[] array = new int[]{2, 2, 2, 7, 8, 8, 11};
        System.out.println(findMode(array));
//        System.out.println(binarySearch(array, 8));
    }
}
