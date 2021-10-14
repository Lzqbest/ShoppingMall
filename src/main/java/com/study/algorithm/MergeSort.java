package com.study.algorithm;

import java.util.Arrays;

/**
 * @author liaozhiqiang1
 * @date 2021/9/3 15:54
 * @Description 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {0, 1, 4, 5, 6, 2, 3, 7, 8, 9};
        sort(arr, 0, 9);
        Arrays.stream(arr).forEach(System.out::println);
    }

    /**
     * TODO 归并排序： 使用递归思想
     * TODO 1、假定某中间值左 右为有序数组
     * TODO 2、创建临时数组
     * TODO 3、从左右两个数组首值开始比较
     * TODO 4、若 左数组数值小 则左数组数值存入临时数组下标位置  左数组下标+1  临时数组下标+1
     * TODO 5、若 右数组数值小 则右数组数值存入临时数组下标位置  右数组下标+1  临时数组下标+1
     * @param arr
     * @param left
     * @param right
     */
    public static void sort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        //分两半
        int mid = left + (right - left) / 2;
        //左排序
        sort(arr, left, mid);
        //右排序
        sort(arr, mid + 1, right);
        //归并
        merge(arr, left, mid + 1, right);
    }

    public static void merge(int[] arr, int leftPoint, int rightPoint, int rightBound) {
        int mid = rightPoint;
        int i = leftPoint;
        int j = mid;

        int k = 0;
        int[] temp = new int[rightBound - leftPoint + 1];

        while (i < mid && j <= rightBound) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }

        while (i < mid){
            temp[k++] = arr[i++];
        }
        while (j <= rightBound){
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[leftPoint + x] = temp[x];
        }
    }
}
