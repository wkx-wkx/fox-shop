package com.fh.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fh.admin.entity.UmsAdmin;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author wkx
 * @since 2020-12-16
 */
public interface IUmsAdminService extends IService<UmsAdmin> {

    List<UmsResource> queryResourceByAdmin(Long id,String username);

    List<UmsRole> queryRoleByAdmin(Long id,String username);

    String login(String username, String password);

    UmsAdmin getUserByName(String username);
}
