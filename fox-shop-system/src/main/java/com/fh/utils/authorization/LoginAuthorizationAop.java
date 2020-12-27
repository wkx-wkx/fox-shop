package com.fh.utils.authorization;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fh.utils.CommonsReturn;
import com.fh.utils.LoginJwtUtils;
import com.fh.utils.ReturnCode;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author wkx
 * @create 2020-12-07 18:44
 */
/*@Aspect
@Component
@Order(4)*/
public class LoginAuthorizationAop {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LoginJwtUtils loginJwtUtils;
    @Autowired
    private RedisTemplate  redisTemplate;

    private  static  final String pre_token="ACCESS_TOKEN";

    @Around(value = "execution(* com.fh.*.controller..*.*(..)) && @annotation(loginAuthorization)")
    public Object logAround(ProceedingJoinPoint joinPoint,LoginAuthorization loginAuthorization) throws Throwable {

        /*token被清空*/
        String token =  request.getHeader("Authorization-token");
        if (StringUtils.isBlank(token)){
            return CommonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
        //eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJjcmVhdGV0aW1lIjoxNjA4NDc5MTQwOTc2LCJleHAiOjE2MDk0NzkxNDAsInVzZXJuYW1lIjoidGVzdCJ9.R2Kz3gH1PEThC5Q0Ti_5xfWsC7bI1sOsxF6yNd8FA3U"
        CommonsReturn commonsReturn  = loginJwtUtils.authToken(token);
        if(commonsReturn.getCode() != 200){
            return commonsReturn;
        }
        //获取用户信息
        Claims claims = (Claims) commonsReturn.getData();
        String userId = String.valueOf(claims.get("userId"));
        String accessKey = pre_token+userId+":"+token;
        if(!redisTemplate.hasKey(accessKey)){
            return commonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
        Long currentTime = System.currentTimeMillis();
        //存在就要续签
        redisTemplate.opsForValue().set(accessKey,currentTime);
        redisTemplate.expire(accessKey,2, TimeUnit.HOURS);

            Object obj = null;
        try {
            obj =joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
        return obj;
    }


}
