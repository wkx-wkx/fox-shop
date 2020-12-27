package com.fh.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.member.entity.UmsMemberLevel;
import com.fh.member.service.IUmsMemberLevelService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/ums/member/level")
public class UmsMemberLevelController {

    @Autowired
    private IUmsMemberLevelService memberLevelService;

    @GetMapping("/{defaultStatus}")
    @LoginAuthorization
    public CommonsReturn queryMemberLevel(@PathVariable("defaultStatus") Integer defaultStatus){
        QueryWrapper<UmsMemberLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("default_status",defaultStatus);
        List<UmsMemberLevel> levelList = memberLevelService.list(queryWrapper);
        return CommonsReturn.success(levelList);
    }

}
