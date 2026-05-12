package com.example.common.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.BusinessService;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Resource
    private BusinessService businessService;

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 获取请求的URL
        String url = request.getRequestURI();
        
        // 2. 判断是否是白名单路径
        if (isWhitelist(url)) {
            return true;  // 直接放行
        }

        // 3. 获取token
        String token = request.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            throw new CustomException("401", "未获取到token, 请重新登录");
        }

        // 4. 验证token
        try {
            JWT.decode(token);
            return true;
        } catch (Exception e) {
            throw new CustomException("401", "token验证失败，请重新登录");
        }
    }

    /**
     * 判断是否是白名单路径
     */
    private boolean isWhitelist(String url) {
        List<String> whitelist = Arrays.asList(
                "/",
                "/login",
                "/register",
                "/error"
        );
        
        // 检查是否是文件上传路径
        if (url.startsWith("/files/")) {
            return true;
        }
        
        // 检查是否是骑手申请相关路径
        if (url.startsWith("/riderApply/")) {
            return true;
        }

        return whitelist.contains(url);
    }
}