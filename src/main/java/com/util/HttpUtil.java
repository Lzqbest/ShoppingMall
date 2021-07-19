package com.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@Slf4j
@Component
public class HttpUtil {

    @Resource
    private HttpClient client;

    public String httpGet(String urlParam) {
        if (StringUtils.isBlank(urlParam)) {
            log.error("Get请求失败！urlParam链路为空！");
            return null;
        }
        GetMethod getMethod = new GetMethod(urlParam);
        try {
            int status = client.executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {
                return getMethod.getResponseBodyAsString();
            }
            log.error("Get请求失败！urlParam【{}】,HttpStatus【{}】", urlParam, status);
        } catch (IOException e) {
            log.error("Get请求失败！urlParam【{}】,ExceptionMessage【{}】", urlParam, e.getMessage());
        } finally {
            //关闭请求
            getMethod.releaseConnection();
        }
        return null;
    }

    public String httpPost(String urlParam, JSONObject body) {
        if (StringUtils.isBlank(urlParam)) {
            log.error("POST请求失败！urlParam链路为空！");
            return null;
        }
        PostMethod postMethod = new PostMethod(urlParam);

        if (!Objects.isNull(body)) {
            try {
                StringRequestEntity entity = new StringRequestEntity(body.toString(), "application/json", "utf-8");
                postMethod.setRequestEntity(entity);
            } catch (UnsupportedEncodingException e) {
                log.error("POST请求body参数构建失败！[{}]", e.getMessage());
            }
        }
        try {
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            }
            log.error("POST请求失败！urlParam【{}】,HttpStatus【{}】", urlParam, status);
        } catch (IOException e) {
            log.error("POST请求失败！urlParam【{}】,ExceptionMessage【{}】", urlParam, e.getMessage());
        } finally {
            //关闭请求
            postMethod.releaseConnection();
        }
        return null;
    }

    @Bean
    public HttpClient initHttpClient() {
        HttpClient client = new HttpClient();
        //设置连接超时时间
        HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
        params.setConnectionTimeout(10000);
        params.setSoTimeout(10000);
        params.setMaxTotalConnections(50);
        params.setDefaultMaxConnectionsPerHost(20);
        return client;
    }

}
