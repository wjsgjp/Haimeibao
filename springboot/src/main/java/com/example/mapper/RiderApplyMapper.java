package com.example.mapper;

import com.example.entity.RiderApply;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RiderApplyMapper {
    /**
     * 新增
     */
    int insert(RiderApply riderApply);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 批量删除
     */
    int deleteBatch(List<Integer> ids);

    /**
     * 修改
     */
    int updateById(RiderApply riderApply);

    /**
     * 根据ID查询
     */
    RiderApply selectById(Integer id);

    /**
     * 查询所有
     */
    List<RiderApply> selectAll(RiderApply riderApply);
} 