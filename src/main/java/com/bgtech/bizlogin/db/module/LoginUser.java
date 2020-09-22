package com.bgtech.bizlogin.db.module;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_user")
public class LoginUser implements Serializable, UserDetails {

    @TableId
    private Integer uid;

    private String username;

    private String password;

    private String phone;

    private String token;

    private Set<Role> roleSet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 是否是已过期的用户的凭据
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否被禁用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
