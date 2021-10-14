package com.controller;

import com.common.annotation.AutoLogin;
import com.common.BaseException;
import com.common.R;
import com.common.ResponseObject;
import com.domain.entity.TbUser;
import com.service.TbUserService;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@Slf4j
@RestController
public class DemoController {

    @Resource
    private TbUserService tbUserService;

    @Resource
    private Cache<String, ResponseObject> cache;

    @AutoLogin
    @GetMapping(value = "/getUser")
    public R getUser(String str) {
        log.info(str);
      /*  log.info("info日志输出");
        for (int i = 0; i <= 10; i++) {
            log.error("错误日志！【{}】", i);
        }*/
        //tbUserService.addUser();
        // Page<TbUser> page = tbUserService.page(new UserQuery());
        //return BaseOutput.success(page);
        //tbUserService.update(new TbUser());
        return null;
    }

    @GetMapping("/insert")
    public R insert() {
        try {
            tbUserService.insertUser();
        } catch (Exception e) {
            log.error("捕获异常！[{}]", e.getMessage());
        }
        return R.success(tbUserService.queryAllUser());
    }

    @GetMapping(value = "/getCache")
    public R getCache() {
        ResponseObject obj = cache.get("obj");
        if (Objects.isNull(obj)) {
            obj = new ResponseObject();
            //obj.setValue(new UserInfo(1, "admin=====111"));
            cache.put("obj", obj);
        }
        return R.success(obj.getValue());
    }

    @GetMapping(value = "/getUserInfo")
    public TbUser getUserInfo() {
        TbUser info = new TbUser();
        if (!Objects.isNull(info)) {
            //TODO 测试自定义异常controller拦截切面
            throw new BaseException(1009, "自定义异常");
        }
        return info;
    }
}
