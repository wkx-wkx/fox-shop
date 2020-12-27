package com.fh.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/ums/umsAdmin")
public class UmsAdminController {

    @Autowired
    private IUmsAdminService adminService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn queryPageAdmin(Page<UmsAdmin> page){
        IPage<UmsAdmin> ipage = adminService.page(page);
        return CommonsReturn.success(ipage);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn createAdmin(@RequestBody UmsAdmin admin){
        if(admin.getId() == null){
            admin.setPassword(new BCryptPasswordEncoder().encode("123"));
            admin.setCreateTime(new Date());
        }
        adminService.saveOrUpdate(admin);
        return CommonsReturn.success();
    }


    @GetMapping("getAdminById")
    @LoginAuthorization
    public CommonsReturn getAdminById(Long id){
        UmsAdmin admin = adminService.getById(id);
        return CommonsReturn.success(admin);
    }

    @DeleteMapping
    @LoginAuthorization
    public CommonsReturn deleteAdmin(Long id){
        adminService.removeById(id);
        return CommonsReturn.success();
    }

    @PutMapping("updateStatus")
    @LoginAuthorization
    public CommonsReturn updateStatus(UmsAdmin admin){
        adminService.updateById(admin);
        return CommonsReturn.success();
    }

}
