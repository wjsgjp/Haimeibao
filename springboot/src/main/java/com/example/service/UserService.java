package com.example.service;

import com.example.entity.Account;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.ArrayList;

public interface UserService {
    /**
     * 新增
     */
    void add(User user);

    /**
     * 删除
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 修改
     */
    void updateById(User user);

    /**
     * 根据ID查询
     */
    User selectById(Integer id);

    /**
     * 查询所有
     */
    List<User> selectAll(User user);

    /**
     * 分页查询
     */
    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 用户登录
     */
    Account login(Account account);

    /**
     * 注册用户
     */
    void register(Account account);

    /**
     * 更新用户角色
     */
    void updateUserRole(Integer userId, String role);

    /**
     * 获取联系人列表
     */
    List<User> getContacts(Integer userId);
}