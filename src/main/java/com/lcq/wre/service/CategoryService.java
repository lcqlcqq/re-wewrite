package com.lcq.wre.service;

import com.lcq.wre.dto.CategoryVo;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CategoryParam;

public interface CategoryService {
    /**
     * 根据id查询文章分类
     * @param id
     * @return
     */
    CategoryVo findCategoryById(Long id);

    /**
     * 所有文章分类
     * @return
     */
    Result findAll();

    /**
     * 导航-文章分类
     * @return
     */
    Result findAllDetail();

    /**
     * 文章分类详情
     * @param id
     * @return
     */
    Result categoriesDetailById(Long id);

    /**
     * 查询所有类别的图标的路径
     * @return
     */
    Result addCategory(CategoryParam categoryParam);

    /**
     * 删除类别
     * @param id
     * @return
     */
    Result deleteCategory(String id);

    /**
     * 修改类别
     * @param categoryParam
     * @return
     */
    Result modifyCategory(CategoryParam categoryParam);
}
