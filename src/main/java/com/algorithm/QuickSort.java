package com.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 10:40
 * @Description
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 9, 2, 3, 7, 8, 10};
        sort(arr, 0, arr.length - 1);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void sort(int[] arr, int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return;
        }
        int mid = partition(arr, leftBound, rightBound);
        //左边
        sort(arr, leftBound, mid - 1);
        //右边
        sort(arr, mid + 1, rightBound);
    }

    public static int partition(int[] arr, int leftBound, int rightBound) {
        int pivot = arr[rightBound];
        int left = leftBound;
        int right = rightBound - 1;
        while (left <= right) {
            while (arr[left] <= pivot && left <= right) {
                left++;
            }
            while (arr[right] > pivot && left < right) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
            }
            if (left == right) {
                break;
            }
        }
        swap(arr, left, rightBound);
        return left;
    }

    public static void swap(int[] arr, int i, int j) {
        int num = arr[i];
        arr[i] = arr[j];
        arr[j] = num;
    }
}
