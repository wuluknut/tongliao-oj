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

package com.wuluknut.tongliaooj.core.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 响应结果视图类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class ResultVO<T> {


    /**
     * 响应时间戳
     */
    private String timestamp;

    /**
     * 响应状态码
     */
    private int status;

    /**
     * 响应内容
     */
    private T content;

    /**
     * 响应消息
     */
    private String message;

    private static <T> ResultVO<T> result(int status, T content, String message) {
        return ResultVO.<T>builder().timestamp(LocalDateTime.now().toString()).status(status).content(content).message(message).build();
    }

    public static <T> ResultVO<T> success(T content, String message) {
        return result(HttpStatus.OK.value(), content, message);
    }

    public static <T> ResultVO<T> success(T content) {
        return success(content, "操作成功！");
    }

    public static <T> ResultVO<T> success() {
        return success(null);
    }

    public static ResultVO<HttpStatus> failure(HttpStatus status, String message) {
        return result(status.value(), status, message);
    }

    public static ResultVO<HttpStatus> failure(String message) {
        return failure(HttpStatus.BAD_REQUEST, message);
    }
}
