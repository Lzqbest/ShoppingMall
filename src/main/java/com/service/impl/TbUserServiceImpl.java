package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.BaseException;
import com.domain.entity.TbUser;
import com.mapper.TbUserMapper;
import com.domain.query.UserQuery;
import com.service.TbUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean addUser() {
        TbUser user = new TbUser();
        user.setName("zhangsan");
        user.setPhone("12345678912");
        user.setOpenId("open12345");
        LocalDateTime now = LocalDateTime.now();
        user.setLastLoginTime(now);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        if (tbUserMapper.insert(user) > 0){
            throw new BaseException("自定义异常！");
        }
        return true;
    }

    @Override
    public TbUser getUserById() {
        LambdaQueryWrapper<TbUser> wrapper = Wrappers.lambdaQuery(TbUser.class);
        wrapper.select(TbUser::getName, TbUser::getPhone)
                .eq(TbUser::getId, "1")
                .eq(TbUser::getIsDel, "0");
        return tbUserMapper.selectOne(wrapper);
    }

    @Override
    public Page<TbUser> page(UserQuery query) {
        LambdaQueryWrapper<TbUser> wrapper = Wrappers.lambdaQuery(TbUser.class);
        wrapper.eq(TbUser::getIsDel, "0")
                .orderByDesc(TbUser::getId);
        return tbUserMapper.selectPage(query, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean update(TbUser tbUser) {
        TbUser t1 = tbUserMapper.selectById(1);
        int i = tbUserMapper.deleteById(t1.getId());
        TbUser t2 = tbUserMapper.selectById(t1.getId());
        if (i == 1) {
            throw new BaseException("11");
        }
        return true;
    }

    @Override
    public List<TbUser> queryAllUser() {
        LambdaQueryWrapper<TbUser> wrapper = Wrappers.lambdaQuery(TbUser.class);
        return tbUserMapper.selectList(wrapper);
    }

    @Override
    public boolean insertUser() {
        return addUser();
    }
}
