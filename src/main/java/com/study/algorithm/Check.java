package com.study.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author liaozhiqiang1
 * @date 2021/9/6 15:41
 * @Description
 */
public class Check {
    public static void main(String[] args) {
        Random random = new Random();
        int num = 10000;
        while (num > 0) {
            int[] arr = new int[20];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(20);
            }
            //选择排序
            //SelectionSort.sort(arr);
            //冒泡排序
            //BubbleSort.sort(arr);
            //插入排序
            //InsertionSort.sort(arr);
            //快速排序
            //QuickSort.sort(arr, 0, arr.length - 1);
            //计数排序
            //CountSort.sort(arr);
            //校验
            check(arr);
            num--;
        }
    }

    public static void check(int[] arr) {
        for (int j = 0; j < arr.length - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                String result = Arrays.stream(arr).mapToObj(Integer::toString).collect(Collectors.joining(","));
                System.out.println(result);
                throw new RuntimeException("排序有误！");
            }
        }
    }
}
