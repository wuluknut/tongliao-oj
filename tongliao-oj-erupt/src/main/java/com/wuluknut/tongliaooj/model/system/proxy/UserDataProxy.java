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

package com.wuluknut.tongliaooj.model.system.proxy;

import com.wuluknut.tongliaooj.model.system.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;

import javax.annotation.PostConstruct;

/**
 * 学生管理数据代理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Service
public class UserDataProxy implements DataProxy<User> {

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void beforeAdd(User user) {
        if (!StringUtils.isBlank(user.getPasswordView())) {
            user.setPassword(encode(user.getPasswordView()));
        } else {
            user.setPassword(encode(user.getPhone()));
        }
    }

    @Override
    public void beforeUpdate(User user) {
        if (!StringUtils.isBlank(user.getPasswordView())) {
            user.setPassword(encode(user.getPasswordView()));
        }
    }

    private String encode(String raw) {
        return "{bcrypt}" + passwordEncoder.encode(raw);
    }
}
