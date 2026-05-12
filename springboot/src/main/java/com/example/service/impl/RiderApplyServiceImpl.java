package com.example.service.impl;

import com.example.entity.RiderApply;
import com.example.mapper.RiderApplyMapper;
import com.example.service.RiderApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiderApplyServiceImpl implements RiderApplyService {

    @Resource
    private RiderApplyMapper riderApplyMapper;

    @Override
    public void add(RiderApply riderApply) {
        riderApplyMapper.insert(riderApply);
    }

    @Override
    public void deleteById(Integer id) {
        riderApplyMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        riderApplyMapper.deleteBatch(ids);
    }

    @Override
    public void updateById(RiderApply riderApply) {
        riderApplyMapper.updateById(riderApply);
    }

    @Override
    public RiderApply selectById(Integer id) {
        return riderApplyMapper.selectById(id);
    }

    @Override
    public List<RiderApply> selectAll(RiderApply riderApply) {
        return riderApplyMapper.selectAll(riderApply);
    }

    @Override
    public PageInfo<RiderApply> selectPage(RiderApply riderApply, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RiderApply> list = riderApplyMapper.selectAll(riderApply);
        return PageInfo.of(list);
    }
} 