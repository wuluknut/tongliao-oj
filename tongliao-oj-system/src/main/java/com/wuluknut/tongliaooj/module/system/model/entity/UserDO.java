/*
 * Copyright (C) 2024 Wulu Knut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.wuluknut.tongliaooj.module.system.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Table;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 用户实体类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table("sys_user")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class UserDO extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 6399555312037701620L;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 学生姓名
     */
    private String nickname;

    /**
     * 用户密码
     */
    @JsonIgnore
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户状态
     */
    private Boolean status;

    /**
     * 获取用户权限
     *
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * 判断账号是否未锁定
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断账号是否未过期
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断凭证是否未过期
     *
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用
     *
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
