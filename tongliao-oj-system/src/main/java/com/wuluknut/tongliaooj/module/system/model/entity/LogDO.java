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

import com.mybatisflex.annotation.Table;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 日志实体类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table("sys_log")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class LogDO extends BaseEntity {

    private static final long serialVersionUID = -3573203893210645556L;

    /**
     * 请求设备
     */
    private String userAgent;

    /**
     * 请求来源
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private String time;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 请求异常
     */
    private String exception;
}
