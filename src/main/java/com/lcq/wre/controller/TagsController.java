package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.TagParam;
import com.lcq.wre.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags = {Swagger2Config.Tags_Controller})
@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    @ApiOperation("获取最热标签")
//    @Cache(name = "tags_hot")
//    @LogAnnotation(module = "标签", operation = "获取最热标签")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

    @GetMapping
    @ApiOperation("查询所有标签")
//    @Cache(name = "tags_all")
//    @LogAnnotation(module = "标签", operation = "查询所有标签列表")
    public Result findAll() {
        return tagService.findAll();
    }

    @GetMapping("detail")
    @ApiOperation("查询所有标签-导航")
//    @Cache(name = "tags_detail_all")
//    @LogAnnotation(module = "标签", operation = "查询所有标签列表")
    public Result findAllDetail() {
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    @ApiOperation("查询对应标签下的所有文章")
//    @Cache(name = "tags_articles")
//    @LogAnnotation(module = "标签", operation = "查询对应标签下的所有文章")
    public Result findDetailById(@PathVariable("id") Long id) {
        return tagService.findDetailById(id);
    }

    @PostMapping("add")
    @ApiOperation("新建标签")
    public Result addTag(@RequestBody TagParam tagParam) {
        return tagService.addTag(tagParam);
    }

    @PostMapping("modify")
    @ApiOperation("修改标签")
    public Result modifyTag(@RequestBody TagParam tagParam) {
        return tagService.modifyTag(tagParam);
    }

    @PostMapping("delete/{id}")
    @ApiOperation("删除标签")
    public Result deleteTag(@PathVariable("id") String id) {
        return tagService.deleteTag(id);
    }
}
