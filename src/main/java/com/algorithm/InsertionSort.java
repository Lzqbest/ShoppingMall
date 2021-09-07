package com.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 17:50
 * @Description 插入排序
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {10, 1, 14, 5, 6, 2, 3, 7, 18, 9, 1, 4, 15, 6, 12, 3};
        sort(arr);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] > arr[j - 1]) {
                    break;
                }
                swap(arr, j, j - 1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int num = arr[i];
        arr[i] = arr[j];
        arr[j] = num;
    }
}