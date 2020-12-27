package com.fh.config.security.bo;

import com.fh.admin.entity.UmsAdmin;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wkx
 * @create 2020-12-20 19:24
 */
public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;
    private List<UmsResource> resourceList;
    private List<UmsRole> roleList;


    public AdminUserDetails(UmsAdmin umsAdmin,List<UmsResource> resourceList,List<UmsRole> roleList){
        this.umsAdmin = umsAdmin;
        this.resourceList = resourceList;
        this.roleList =roleList;
    }
    /**
     * 查询权限和角色放在集合中
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
        resourceList.forEach(resource -> {
            list.add(new SimpleGrantedAuthority(resource.getKeyword()));
        });
        roleList.forEach(role -> {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getKeyword()));
        });
        return list;
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    /**
     * 获取用户名
     * @return
     */
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    /**
     * 账号超时
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否正确
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否有效
     * @return
     */
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
