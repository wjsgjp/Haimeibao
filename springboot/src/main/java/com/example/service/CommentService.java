package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.OrderStatusEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment;
import com.example.entity.Orders;
import com.example.mapper.CommentMapper;
import com.example.mapper.OrdersMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评价表业务处理
 **/
@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private OrdersMapper ordersMapper;

    /**
     * 新增
     */
    @Transactional
    public void add(Comment comment) {
        Integer orderId = comment.getOrderId();
        comment.setTime(DateUtil.now());
        Orders orders = ordersMapper.selectById(orderId);
        if (ObjectUtil.isNotEmpty(orders)){
            comment.setUserId(orders.getUserId());
            comment.setBusinessId(orders.getBusinessId());
        }
        commentMapper.insert(comment);

        orders.setStatus(OrderStatusEnum.DONE.getValue());
        //把订单的状态改为已完成
        ordersMapper.updateById(orders);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            commentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 拿到当前的登录用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {  // 如果是商家的话   只能查询自己的分类
            comment.setBusinessId(currentUser.getId());  // 设置商家自己的Id作为查询条件
        }
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public List<Comment> selectByBusinessId(Integer id) {
        return commentMapper.selectByBusienssId(id);
    }
}