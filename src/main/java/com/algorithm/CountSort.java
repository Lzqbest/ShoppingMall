package com.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 16:10
 * @Description 计数排序
 */
public class CountSort {

    public static void main(String[] args) {
        int[] arr = {0, 1, 14, 5, 6, 2, 3, 7, 18, 9, 1, 4, 15, 6, 12, 3, 7, 18, 9, 11, 4, 1, 6, 2, 13, 17, 8, 9, 1, 14, 5, 6, 12, 3, 7, 8, 9, 11, 4, 15, 16, 2, 13, 7, 8, 9};
        sort(arr);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void sort(int[] arr) {
        int[] temp = new int[20];
        for (int i = 0; i < arr.length; i++) {
            temp[arr[i]]++;
        }
        int k = 0;
        for (int i = 0; i < temp.length; i++) {
            while (temp[i]-- > 0) {
                arr[k++] = i;
            }
        }
    }
}
