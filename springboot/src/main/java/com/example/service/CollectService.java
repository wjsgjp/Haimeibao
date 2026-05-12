package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.BusinessMapper;
import com.example.mapper.CollectMapper;
import com.example.mapper.OrdersItemMapper;
import com.example.mapper.OrdersMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 业务处理
 **/
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private CommentService commentService;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrdersItemMapper ordersItemMapper;

    /**
     * 新增
     */
    public void add(Collect collect) {
        collectMapper.insert(collect);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Collect collect) {
        collectMapper.updateById(collect);
    }

    /**
     * 根据ID查询
     */
    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Collect> selectAll(Collect collect) {
        List<Collect> collectList = collectMapper.selectAll(collect);
        for (Collect c1 : collectList) {
            Business business = new Business();
            business.setId(c1.getBusinessId());
            business.setStatus("通过");
            Business business1 = businessMapper.selectOne(business);
            List<Comment> c =  commentService.selectByBusinessId(business.getId());
            double sum = c.stream().map(Comment::getStar).reduce(Double::sum).orElse(0D) + 5D;
            double star = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(c.size() + 1), 1, BigDecimal.ROUND_UP).doubleValue();
            business1.setScore(star);
            List<Orders> orders = ordersMapper.selectByBusinessId(business.getId());
            int num = 0;
            for (Orders order : orders) {
                OrdersItem ordersItem = new OrdersItem();
                ordersItem.setOrderId(order.getId());
                List<OrdersItem> ordersItems = ordersItemMapper.selectAll(ordersItem);
                num += ordersItems.stream().map(OrdersItem::getNum).reduce(Integer::sum).orElse(0);
            }
            business1.setNums(num);
            c1.setBusiness(business1);
        }
        return collectList;
    }

    /**
     * 分页查询
     */
    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.BUSINESS.name())) {
            collect.setBusinessId(currentUser.getId());
        }
        List<Collect> list = collectMapper.selectAll(collect);
        return PageInfo.of(list);
    }

    public Collect selectByUserIdAndBusinessId(Integer userId, Integer businessId) {
        return collectMapper.selectByUserIdAndBusinessId(userId, businessId);
    }

    public void saveCollect(Collect collect) {
        Collect dbCollect = this.selectByUserIdAndBusinessId(collect.getUserId(), collect.getBusinessId());
        if (dbCollect != null) {  // 说明收藏过了
            this.deleteById(dbCollect.getId());  //删除收藏
        } else {
            // 新的收藏
            collect.setTime(DateUtil.now());
            this.add(collect);
        }
    }
}