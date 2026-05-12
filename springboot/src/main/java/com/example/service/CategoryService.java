package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 商品分类业务处理
 **/
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BusinessService businessService;

    /**
     * 新增
     */
    public void add(Category category) {
        //验证商家是否通过审核
        businessService.checkBusinessAuth();

        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.BUSINESS.name())){
            category.setBusinessId(currentUser.getId());
        }

        categoryMapper.insert(category);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        //验证商家是否通过审核
        businessService.checkBusinessAuth();

        categoryMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        //验证商家是否通过审核
        businessService.checkBusinessAuth();

        for (Integer id : ids) {
            categoryMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Category category) {
        //验证商家是否通过审核
        businessService.checkBusinessAuth();

        categoryMapper.updateById(category);
    }

    /**
     * 根据ID查询
     */
    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    /**
     * 分页查询
     */
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.BUSINESS.name())){
            category.setBusinessId(currentUser.getId());
        }
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }

}