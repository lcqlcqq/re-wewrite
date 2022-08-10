package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CategoryParam;
import com.lcq.wre.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags = {Swagger2Config.Category_Controller})
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    /**
     * 所有文章分类
     * @return
     */
    @ApiOperation("查询所有的分类信息")
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }

    /**
     * 导航-文章分类
     *
     * @return
     */
    @ApiOperation("查询所有的分类信息-导航")
    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    /**
     * 文章分类详情
     * @param id
     * @return
     */
    @ApiOperation("根据类别id查询分类信息")
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") @ApiParam("文章分类的id") Long id){
        return categoryService.categoriesDetailById(id);
    }
    /**
     *  新建类别
     */
    @ApiOperation("新建文章分类")
    @PostMapping("add")
    public Result addCategory(@RequestBody CategoryParam categoryParam){
        return categoryService.addCategory(categoryParam);
    }
    /**
     * 删除类别
     */
    @ApiOperation("根据id删除文章分类")
    @PostMapping("delete/{id}")
    public Result deleteCategory(@PathVariable("id") @ApiParam("文章分类的id") String id){
        return categoryService.deleteCategory(id);
    }

    /**
     * 修改类别
     * @param categoryParam
     * @return
     */
    @ApiOperation("修改类别信息")
    @PostMapping("modify")
    public Result modifyCategory(@RequestBody CategoryParam categoryParam){
        return categoryService.modifyCategory(categoryParam);
    }

}
