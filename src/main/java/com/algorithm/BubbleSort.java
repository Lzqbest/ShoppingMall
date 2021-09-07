package com.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 17:38
 * @Description 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {0, 1, 14, 5, 6, 2, 3, 7, 18, 9, 1, 4, 15, 6, 12, 3};
        sort(arr);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void sort(int[] arr) {
        boolean flag;
        do {
            flag = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    flag = true;
                }
            }
        } while (flag);
    }

    public static void swap(int[] arr, int i, int j) {
        int num = arr[i];
        arr[i] = arr[j];
        arr[j] = num;
    }
}
