package com.fh.config.security.filter;

import com.fh.utils.CommonsReturn;
import com.fh.utils.LoginJwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wkx
 * @create 2020-12-20 22:14
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private LoginJwtUtils loginJwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;

    private  static  final String pre_token="ACCESS_TOKEN:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization-token");
        //如果token为空就抛出异常
        if(StringUtils.isNotBlank(token)){
            //解析token值
            CommonsReturn commonsReturn = loginJwtUtils.authToken(token);
            if(commonsReturn.getCode() == 200 && SecurityContextHolder.getContext().getAuthentication() == null){
               String userName = loginJwtUtils.getUserName(token);
               //调用权限，角色信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                //
                String accessKey = pre_token+userDetails.getUsername()+":"+token;
                if(redisTemplate.hasKey(accessKey)){
                    //保证对象在其他组件被调用到
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //jwt token值返回给用户
                    Long currentTime = System.currentTimeMillis();
                    //存在就要续签
                    redisTemplate.opsForValue().set(accessKey,currentTime);
                    redisTemplate.expire(accessKey,2, TimeUnit.HOURS);
                }

            }
        }
        filterChain.doFilter(request,response);
    }
}
