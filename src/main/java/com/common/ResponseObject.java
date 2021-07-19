package com.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Best-Traveler
 * @Date 2020/11/6
 * @Description 缓存基类
 **/
@Data
public class ResponseObject<T> implements Serializable {

    private static final Long serialVersionUID = 1L;

    private T value;

}
