package com.example.service;

import com.example.entity.AmountDTO;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.mapper.CartMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 购物车业务处理
 **/
@Service
public class CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsService goodsService;

    @Resource
    private BusinessService businessService;

    // 计算购物车的总金额
    public AmountDTO calc(Integer userId, Integer businessId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setBusinessId(businessId);
        // 查出当前用户在某商家的所有购物车商品数据
        List<Cart> cartList = this.selectAll(cart);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal actual = BigDecimal.ZERO;
        for (Cart c : cartList) {
            Goods goods = c.getGoods();
            if (goods != null) {
                BigDecimal price = goods.getPrice();
                BigDecimal actualPrice = goods.getMoney();
                amount = amount.add(price.multiply(BigDecimal.valueOf(c.getNum())));  // 原价
                actual = actual.add(actualPrice.multiply(BigDecimal.valueOf(c.getNum())));  // 打折之后的价格
            }
        }
        AmountDTO amountDTO = new AmountDTO();
        amountDTO.setAmount(amount);
        amountDTO.setMoney(actual);
        amountDTO.setDiscount(amount.subtract(actual));  // 优惠的金额
        return amountDTO;
    }

    /**
     * 新增
     */
    public void add(Cart cart) {
        cartMapper.insert(cart);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        cartMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            cartMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Cart cart) {
        cartMapper.updateById(cart);
    }

    /**
     * 根据ID查询
     */
    public Cart selectById(Integer id) {
        return cartMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Cart> selectAll(Cart cart) {
        List<Cart> cartList = cartMapper.selectAll(cart);
        for (Cart showCart : cartList) {
            Goods goods = goodsService.selectById(showCart.getGoodsId());
            getMoney(goods);
            showCart.setGoods(goods);
            showCart.setBusiness(businessService.selectByBusinessId(showCart.getBusinessId()));
        }
        return cartList;
    }

    /**
     * 计算商品的折扣
     *
     * @param goods
     */
    public void getMoney(Goods goods) {
        BigDecimal money = goods.getPrice().multiply(BigDecimal.valueOf(goods.getDiscount()).setScale(2, RoundingMode.UP));
        goods.setMoney(money);
    }


    /**
     * 分页查询
     */
    public PageInfo<Cart> selectPage(Cart cart, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Cart> list = selectAll(cart);
        return PageInfo.of(list);
    }

    public void detByBusinessId(Integer businessId, Integer userId) {
        cartMapper.detByBusinessId(businessId, userId);
    }

    /**
     * 根据用户id和商家id 查询全部的购物车
     * @param id
     * @param businessId
     * @return
     */
    public List<Cart> selectUserCart(Integer id, Integer businessId) {
        Cart cart = new Cart();
        cart.setUserId(id);
        cart.setBusinessId(businessId);
        return this.selectAll(cart);
    }
}