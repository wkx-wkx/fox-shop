package com.fh.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.LoginJwtUtils;
import com.fh.utils.MD5Util;
import com.fh.utils.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;



import java.util.concurrent.TimeUnit;

/**
 * @author wkx
 * @create 2020-12-06 21:24
 */
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private IUmsAdminService adminService;
    @Autowired
    private RedisTemplate redisTemplate;

    private  static  final String pre_token="ACCESS_TOKEN";

    @PostMapping
    public CommonsReturn doLogin(@RequestParam("username") String username, @RequestParam("password")String password){
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return CommonsReturn.error(ReturnCode.USERNAME_PASSWORD_NULL);
        }
        String token = adminService.login(username,password);
        if(token == null){
            return CommonsReturn.error(ReturnCode.LOGIN_ERROR);
        }
        //根据用户名查询用户信息
        /*UmsAdmin user = adminService.getOne(new QueryWrapper<UmsAdmin>().eq("username",username));
        if (user == null){
            return CommonsReturn.error(ReturnCode.USERNAME_NOEXIST);
        }
        //判断用户密码是否正确
        String passwordMd5 = MD5Util.MD5Encode(password);
        if (!passwordMd5.equals(user.getPassword())){
            return CommonsReturn.error(ReturnCode.PASSWORD_ERROR);
        }
        String token = loginJwtUtils.createToken(user);
       */
        return CommonsReturn.success(token);
    }


}
