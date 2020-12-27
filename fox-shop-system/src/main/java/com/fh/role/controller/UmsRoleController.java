package com.fh.role.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.role.entity.UmsRole;
import com.fh.role.service.IUmsRoleService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/role")
public class UmsRoleController {

    @Autowired
    private IUmsRoleService roleService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn queryPageRole(Page<UmsRole> page){
        IPage<UmsRole> ipage = roleService.page(page);
        return CommonsReturn.success(ipage);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn createRole(@RequestBody UmsRole role){
        roleService.saveOrUpdate(role);
        return CommonsReturn.success();
    }

    @GetMapping("getRoleById")
    @LoginAuthorization
    public CommonsReturn getRoleById(Long id){
        UmsRole role = roleService.getById(id);
        return CommonsReturn.success(role);
    }

    @DeleteMapping
    @LoginAuthorization
    public CommonsReturn deleteRole(Long id){
        roleService.removeById(id);
        return CommonsReturn.success();
    }

    @PutMapping("updateStatus")
    @LoginAuthorization
    public CommonsReturn updateStatus(UmsRole role){
        roleService.updateById(role);
        return CommonsReturn.success();
    }




}
