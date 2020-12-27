package com.fh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.mapper.UmsAdminMapper;
import com.fh.admin.service.IUmsAdminService;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import com.fh.utils.LoginJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author wkx
 * @since 2020-12-16
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService{

    @Resource
    private UmsAdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginJwtUtils loginJwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;
    private  static  final String pre_token="ACCESS_TOKEN:";
    private static final  String admin_login="LOGIN:";

    @Override
    public List<UmsResource> queryResourceByAdmin(Long id,String username) {
        String key = admin_login+username+":RESOURCE";
        if(redisTemplate.hasKey(key)){
            return (List<UmsResource>) redisTemplate.opsForValue().get(key);
        }
        List<UmsResource> list = adminMapper.queryResourceByAdmin(id);
        redisTemplate.opsForValue().set(key,list);
        redisTemplate.expire(key,2,TimeUnit.HOURS);
        return list;
    }

    @Override
    public List<UmsRole> queryRoleByAdmin(Long id,String username) {
        String key = admin_login+username+":ROLE";
        if(redisTemplate.hasKey(key)){
            return (List<UmsRole>) redisTemplate.opsForValue().get(key);
        }
        List<UmsRole> list = adminMapper.queryRoleByAdmin(id);
        redisTemplate.opsForValue().set(key,list);
        redisTemplate.expire(key,2,TimeUnit.HOURS);
        return list;
    }

    @Override
    public UmsAdmin getUserByName(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        String key = admin_login+username+":USER";
        if(redisTemplate.hasKey(key)){
            return (UmsAdmin) redisTemplate.opsForValue().get(key);
        }
        UmsAdmin admin = getOne(queryWrapper);
        redisTemplate.opsForValue().set(key,admin);
        redisTemplate.expire(key,2,TimeUnit.HOURS);
        return admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            //清空之前这个用户的登录信息
            cleanAdminRedis(username);
            //触发登录认证方法
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //判断密码是否正确
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("用户密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
           //让这个对象实现上下文传递共享
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //生成token值
            token = loginJwtUtils.createToken(userDetails);
            //需要将上次的token值删除
            String currentTime = String.valueOf(System.currentTimeMillis());
            String accessKey = pre_token+userDetails.getUsername()+":"+token;
            redisTemplate.opsForValue().set(accessKey,currentTime);
            redisTemplate.expire(accessKey,2, TimeUnit.HOURS);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return token;
    }


    public void cleanAdminRedis(String username){
        //清理token信息
        String tokenKey = pre_token+username+":*";
        Set<String> tokenKeys = redisTemplate.keys(tokenKey);
        if(!CollectionUtils.isEmpty(tokenKeys)){
            redisTemplate.delete(tokenKeys);
        }
        //进行用户的登录的信息
        String adminKey = admin_login+username+":*";
        Set<String> keys = redisTemplate.keys(adminKey);
        if(!CollectionUtils.isEmpty(keys)){
            redisTemplate.delete(keys);
        }

    }


}
