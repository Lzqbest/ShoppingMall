package com.common.config;

import com.common.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Objects;

/**
 * @Author Best-Traveler
 * @Date 2020/11/6
 * @Description
 **/
@Slf4j
@Configuration
public class EhcacheConfig {
    /**
     * 缓存中key的数量
     */
    @Value("${cache.ehcache.heap}")
    private Long heap;
    /**
     * 缓存内存大小 50M
     */
    @Value("${cache.ehcache.offheap}")
    private Long offheap;
    /**
     * 缓存过期时间 单位 秒(S) 默认两小时
     */
    @Value("${cache.ehcache.ttl}")
    private Long ttl;

    /**
     * 緩存別名
     */
    private final String DEFAULT_CACHE_ALIAS = "Mall_Cache";


    @Bean
    public Cache<String, ResponseObject> initEhCache() {
        //缓存参数   可持久化到本地磁盘  需要增加disk(持久化大小) with(持久化地址)
        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(heap, EntryUnit.ENTRIES)
                .offheap(offheap, MemoryUnit.MB);
        //创建缓存管理器构造器
        CacheManagerBuilder cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder();

        // timeToLiveExpiration:以创建时间为基准开始计算的超时时长
        // timeToIdleExpiration:在创建时间和最近访问时间中取出离现在最近的时间作为基准计算的超时时长
        ExpiryPolicy expiryPolicy = ttl > 0 ? ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ttl)) : ExpiryPolicyBuilder.noExpiration();

        //缓存配置
        CacheConfiguration configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                ResponseObject.class,
                resourcePoolsBuilder)
                .withExpiry(expiryPolicy).build();

        //返回初始化的缓存管理器
        CacheManager cacheManager = cacheManagerBuilder.withCache(DEFAULT_CACHE_ALIAS, configuration).build(true);

        Cache<String,ResponseObject> cache = cacheManager.getCache(DEFAULT_CACHE_ALIAS,String.class,ResponseObject.class);

        if (Objects.isNull(cache)){
            log.error("Ehcache 缓存初始化失败！cache is null");
        }
        log.debug("Ehcache 缓存初始化成功！");

        return cache;
    }

}
