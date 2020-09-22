package com.bgtech.bizlogin.common.filter;

import com.bgtech.bizlogin.common.token.SmsAuthenticationToken;
import com.bgtech.bizlogin.db.module.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangJiefeng
 * @date 2020/9/21 0019 9:40
 * Spring Security登录认证过滤器，登录成功后生成一个Token放入header返回给客户端
 * 区分手机验证码登录和帐号密码登录
 */
public class MobileLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public MobileLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 拦截用户密码登录请求进行处理
        super.setFilterProcessesUrl("/login/mobile");
    }

    /**
     * 接收并解析用户凭证，若出现错误，返回json数据到前端
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        System.out.println("mobile登录");
        try {
            LoginUser user = new ObjectMapper().readValue(req.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new SmsAuthenticationToken(
                            new ArrayList<>(),
                            user.getPhone()
                    )
            );
        } catch (Exception e) {
            try {
                // 未登录或者密码错误时，返回json提示
                res.setContentType("application/json;charset=utf-8");
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = res.getWriter();
                Map<String,Object> map = new HashMap<>();
                map.put("code",HttpServletResponse.SC_UNAUTHORIZED);
                map.put("message","账号或密码错误！");
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户登录成功后，生成token,并且返回json数据给前端
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth){
        System.out.println("登录成功");
        //json web token构建
        String token = Jwts.builder()
                //此处为自定义的、实现org.springframework.security.core.userdetails.UserDetails的类，需要和配置中设置的保持一致
                //此处的subject可以用一个用户名，也可以是多个信息的组合，根据需要来定
                .setSubject(((LoginUser) auth.getPrincipal()).getUsername())
                //设置token过期时间，24小時
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))

                //设置token签名、密钥
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")

                .compact();

        //返回token
        res.addHeader("Authorization", "Bearer" + " " + token);

        try {
            //登录成功時，返回json格式进行提示
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = res.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("code",HttpServletResponse.SC_OK);
            map.put("message","登陆成功！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
