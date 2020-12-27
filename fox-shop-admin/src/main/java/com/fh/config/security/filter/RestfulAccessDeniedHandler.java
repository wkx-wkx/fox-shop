package com.fh.config.security.filter;

import cn.hutool.json.JSONUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录没有通过的返回结果
 * @author wkx
 * @create 2020-12-21 0:27
 */
@Component
public class  RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonsReturn.error(ReturnCode.NO_PERMISSION)));
        response.getWriter().flush();

    }
}
