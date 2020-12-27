package com.fh.config.security.filter;

import cn.hutool.json.JSONUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wkx
 * @create 2020-12-21 0:36
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonsReturn.error(ReturnCode.LOGIN_DISABLED)));
        response.getWriter().flush();
    }
}
