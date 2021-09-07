package com.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 17:22
 * @Description 选择排序
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {0, 1, 14, 5, 6, 2, 3, 7, 18, 9, 1, 4, 15, 6, 12, 3};
        sort(arr, 0);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void sort(int[] arr, int firstBound) {
        if (firstBound >= arr.length) {
            return;
        }
        selection(arr, firstBound);
        sort(arr, ++firstBound);
    }

    public static void selection(int[] arr, int firstBound) {
        int k = firstBound;
        for (int i = firstBound + 1; i < arr.length; i++) {
            if (arr[k] > arr[i]) {
                k = i;
            }
        }
        swap(arr, firstBound, k);
    }

    public static void swap(int[] arr, int i, int j) {
        int num = arr[i];
        arr[i] = arr[j];
        arr[j] = num;
    }
}