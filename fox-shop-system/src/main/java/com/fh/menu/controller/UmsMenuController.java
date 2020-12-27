package com.fh.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.menu.entity.UmsMenu;
import com.fh.menu.service.IUmsMenuService;
import com.fh.model.search.MenuSearch;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/menu")
public class UmsMenuController {

    @Autowired
    private IUmsMenuService menuService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn queryPageMenu(Page<UmsMenu> page, MenuSearch menuSearch){
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>();
        if(menuSearch.getPid() != null){
            queryWrapper.eq("parent_id",menuSearch.getPid());
        }else {
            queryWrapper.eq("level",menuSearch.getLevel());
        }
        if(StringUtils.isNotBlank(menuSearch.getName())){
            queryWrapper.like("name",menuSearch.getName());
        }
        IPage<UmsMenu> ipage = menuService.page(page,queryWrapper);
        return CommonsReturn.success(ipage);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn createMenu(@RequestBody UmsMenu menu){
        if(menu.getParentId() == 0){
            menu.setLevel(0);
        }else {
            menu.setLevel(1);
        }
        menuService.saveOrUpdate(menu);
        return CommonsReturn.success();
    }

    @GetMapping("getMenuById")
    @LoginAuthorization
    public CommonsReturn getMenuById(long id){
        UmsMenu menu = menuService.getById(id);
        return CommonsReturn.success(menu);
    }

    @DeleteMapping
    @LoginAuthorization
    public CommonsReturn deleteMenu(Long id){
        menuService.removeById(id);
        return CommonsReturn.success();
    }

    @PutMapping("updateHiddenById")
    @LoginAuthorization
    public CommonsReturn updateHiddenById(UmsMenu menu){
        menuService.updateById(menu);
        return CommonsReturn.success();
    }

    //查询下拉框
    @GetMapping("fetchMenuSelect")
    @LoginAuthorization
    public CommonsReturn fetchMenuSelect(){
        List<UmsMenu> list = menuService.list(new QueryWrapper<UmsMenu>().eq("level",0));
        return CommonsReturn.success(list);
    }

}
