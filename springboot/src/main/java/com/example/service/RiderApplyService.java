package com.example.service;

import com.example.entity.RiderApply;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface RiderApplyService {
    /**
     * 新增
     */
    void add(RiderApply riderApply);

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
    void updateById(RiderApply riderApply);

    /**
     * 根据ID查询
     */
    RiderApply selectById(Integer id);

    /**
     * 查询所有
     */
    List<RiderApply> selectAll(RiderApply riderApply);

    /**
     * 分页查询
     */
    PageInfo<RiderApply> selectPage(RiderApply riderApply, Integer pageNum, Integer pageSize);
} 