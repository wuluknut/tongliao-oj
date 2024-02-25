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

package com.wuluknut.tongliaooj.module.judge.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代码执行系统配置信息类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "judge.server")
public class JudgeProperties {

    /**
     * 代码执行系统主机
     */
    private String host = "judge0_server";

    /**
     * 代码执行系统端口
     */
    private String port = "2358";

    /**
     * 代码执行系统协议
     */
    private String protocol = "http";

    /**
     * 代码执行系统请求头
     */
    private List<Map<String, String>> header = new ArrayList<>();

    /**
     * 获取代码执行系统请求地址
     *
     * @return java.lang.String
     */
    public String getUrl() {
        return protocol + "://" + host + ":" + port + "/submissions/";
    }
}
