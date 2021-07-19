package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@SpringBootApplication
@MapperScan("com.mapper")
@EnableCaching
public class ShoppingMallApplication {

    public static void main(String[] args){
        SpringApplication.run(ShoppingMallApplication.class,args);
    }
}
