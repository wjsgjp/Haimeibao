package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.MessageMapper;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteById(id);
        }
    }

    @Override
    public void updateById(User user) {
        User dbUser2 = userMapper.selectByUsername(user.getUsername());
        //  根据当前更新的用户的账号查询数据库  如果数据库存在跟当前更新用户一样账号的数据  那么当前的更新是不合法的  数据重复了
        if (ObjectUtil.isNotEmpty(dbUser2) && !Objects.equals(dbUser2.getId(), user.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        userMapper.updateById(user);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    @Override
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    @Override
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {   // 比较用户输入密码和数据库密码是否一致
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    @Override
    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);  // 拷贝 账号和密码2个属性
        this.add(user);  // 添加账户信息
    }

    @Override
    public void updateUserRole(Integer userId, String role) {
        User user = new User();
        user.setId(userId);
        user.setRole(role);
        userMapper.updateById(user);
    }

    private Account selectByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        List<User> list = this.selectAll(user);
        return CollUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public List<User> getContacts(Integer userId) {
        // 获取当前用户信息
        User currentUser = selectById(userId);
        if (currentUser == null) {
            throw new CustomException("404", "用户不存在");
        }

        // 根据角色返回不同的联系人列表
        System.out.println("获取到的用户id"+userId+"  "+currentUser.getRole());
        if ("USER".equals(currentUser.getRole())) {
            // 如果是普通用户，返回所有商家
            return userMapper.selectAllBusiness();
        } else if ("BUSINESS".equals(currentUser.getRole())) {
            // 如果是商家，返回有过聊天记录的用户
            List<Integer> contactUserIds = messageMapper.selectContactUserIds(userId);
            if (contactUserIds.isEmpty()) {
                return new ArrayList<>();
            }
            return userMapper.selectByIds(contactUserIds);
        }
        
        return new ArrayList<>();
    }
} 