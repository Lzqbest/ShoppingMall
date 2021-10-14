package com.study.algorithm;

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

    /**
     * TODO 计数排序：主要用于数值范围较小，数据量大的情景  例如数值范围【0-99】
     * TODO 1、创建空数组【temp】 大小为数值范围中个数 【100】
     * TODO 2、遍历排序数组，
     * TODO 3、以数组中数值为下标 将空数组【temp】中值累加1
     * TODO 4、遍历【temp】 将数组中值大于零的下标 按值的个数 依次存入结果集中
     * @param arr
     */
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
