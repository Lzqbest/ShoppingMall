package com.study.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 10:40
 * @Description 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 9, 2, 3, 7, 8, 10};
        sort(arr, 0, arr.length - 1);
        String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println(result);
    }

    /**
     * TODO 快速排序 使用递归思想
     * TODO 假定数组最后一个数值为中间值
     * TODO 分别从数组左右边界开始（左边界递增 右边界递减）
     * TODO 当左边界中存在大于中间值 右边界存在小于中间值 交换左右边界值
     * TODO 左右下标相同时跳出循环
     * TODO 最后将中间值 交换到左下标位置
     * @param arr
     * @param leftBound
     * @param rightBound
     */
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
