package com.example.mapper;

import com.example.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作address相关数据接口
*/
public interface AddressMapper {

    /**
      * 新增
    */
    int insert(Address address);

    /**
      * 删除
    */
    int deleteById(@Param("id") Integer id,@Param("role") String role);

    /**
      * 修改
    */
    int updateById(Address address);

    /**
      * 根据ID查询
    */
    Address selectById(Integer id,String role);

    /**
      * 查询所有
    */
    List<Address> selectAll(Address address);

}