package com.study.algorithm;

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

    /**
     * TODO 冒泡排序
     * TODO 1、初始化标记(flag=false) 变量
     * TODO 2、比较数组相邻两个值大小，若无序 则交换  且修改标记(flag=true)
     * TODO 3、根据标记值 判断此次循环是否存在交换  存在：重复循环   不存在：结束循环
     * @param arr
     */
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
