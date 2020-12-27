package com.fh.utils;

import com.fh.admin.entity.UmsAdmin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wkx
 * @create 2020-12-06 22:21
 */
@Component
public class LoginJwtUtils {

    @Value("${user.jwtset.signature}")
    private String signature;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.type}")
    private String type;
    @Value("${user.jwtset.alg}")
    private String alg;


    public String createToken(UserDetails userDetails){
        //JWTtoken有三部分组成
        //1.头信息
        Map<String,Object> headerMap=new HashMap<>();
        headerMap.put("alg",alg);
        headerMap.put("type",type);
        //有效负载
        Map<String,Object> payloadMap=new HashMap<>();
        payloadMap.put("username",userDetails.getUsername());
        payloadMap.put("createtime",new Date());

        //设置失效时间
        long startTime = System.currentTimeMillis();
        long expTime = startTime+exptime;

        //生成jwt
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payloadMap)
                .setExpiration(new Date(expTime))
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return token;

    }


    public CommonsReturn authToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return CommonsReturn.success(claims);
        }catch (Exception e){
            return CommonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
    }

    public String getUserName(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }catch (Exception e){
            return null;
        }
    }


}

