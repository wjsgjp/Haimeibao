package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作user相关数据接口
 */
public interface UserMapper {

    /**
     * 新增
     */
    int insert(User user);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(User user);

    /**
     * 根据ID查询
     */
    User selectById(Integer id);

    /**
     * 查询所有
     */
    List<User> selectAll(User user);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    /**
     * 查询所有商家用户
     */
    List<User> selectAllBusiness();

    /**
     * 根据用户ID列表查询用户信息
     */
    List<User> selectByIds(@Param("userIds") List<Integer> userIds);

}