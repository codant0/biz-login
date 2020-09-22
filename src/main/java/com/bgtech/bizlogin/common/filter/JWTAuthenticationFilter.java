package com.bgtech.bizlogin.common.filter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 9:46
 *
 * 过滤未登录的访问
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private String excludedSuffix;

    private String TOKEN_SECRET = "MyJwtSecret";
    private String TOKEN_HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 对请求进行过滤
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            // 请求体的头中是否包含Authorization
            String header = request.getHeader(TOKEN_HEADER);
            // Authorization中是否包含Bearer，有一个不包含时直接返回
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                System.out.println("未登录");
                chain.doFilter(request, response);
                responseJson(response);
                return;
            }
            // 获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            // 获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            responseJson(response);
            e.printStackTrace();
        }
    }

    /**
     * 未登录提示
     * @param response
     */
    private void responseJson(HttpServletResponse response){
        try {
            //未登录时，使用json进行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("code",HttpServletResponse.SC_FORBIDDEN);
            map.put("message","请登录！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通过token，获取用户信息
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null) {
            // 通过token解析出用户信息
            String user = Jwts.parser()
                    //签名、密钥
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX + " ", ""))
                    .getBody()
                    .getSubject();
            // 不为null，返回
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

    private boolean ixExcluded(HttpServletRequest request) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(excludedSuffix)) {
            return false;
        }
        return request.getServletPath().endsWith(excludedSuffix);
    }
}
