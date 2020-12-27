package com.fh.config.security;

import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.config.security.bo.AdminUserDetails;
import com.fh.config.security.filter.JwtAuthenticationTokenFilter;
import com.fh.config.security.filter.RestAuthenticationEntryPoint;
import com.fh.config.security.filter.RestfulAccessDeniedHandler;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @author wkx
 * @create 2020-12-20 14:04
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ShopSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUmsAdminService adminService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //过滤器
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login","/upload")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
            //禁用缓存
            http.headers().cacheControl();
            //把自定义的拦截器加载过来
            http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            //自定义未登录或者未授权的结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 用户登录认证和权限授权
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return username->{
            UmsAdmin admin = adminService.getUserByName(username);
            if(admin != null){
                List<UmsResource> resourceList =  adminService.queryResourceByAdmin(admin.getId(),username);
                List<UmsRole> roleList = adminService.queryRoleByAdmin(admin.getId(),username);
                return new AdminUserDetails(admin,resourceList,roleList);
            }
            throw new UsernameNotFoundException("用户不存在");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
