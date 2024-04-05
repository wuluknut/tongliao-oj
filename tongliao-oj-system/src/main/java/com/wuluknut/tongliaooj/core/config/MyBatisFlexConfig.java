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

package com.wuluknut.tongliaooj.core.config;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.logicdelete.impl.BooleanLogicDeleteProcessor;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * Mybatis Flex 配置类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Configuration
@RequiredArgsConstructor
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig flexGlobalConfig) {
        flexGlobalConfig.registerInsertListener(insertListener(), BaseEntity.class);
        flexGlobalConfig.registerUpdateListener(updateListener(), BaseEntity.class);

        LogicDeleteManager.setProcessor(new BooleanLogicDeleteProcessor());
    }

    private InsertListener insertListener() {
        return o -> {
            BaseEntity entity = (BaseEntity) o;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            LocalDateTime dateTime = LocalDateTime.now();

            entity.setUpdateBy(auth.getName());
            entity.setUpdateTime(dateTime);
            entity.setCreateBy(auth.getName());
            entity.setCreateTime(dateTime);
        };
    }

    private UpdateListener updateListener() {
        return o -> {
            BaseEntity entity = (BaseEntity) o;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            LocalDateTime dateTime = LocalDateTime.now();

            entity.setUpdateBy(auth.getName());
            entity.setUpdateTime(dateTime);
        };
    }
}
