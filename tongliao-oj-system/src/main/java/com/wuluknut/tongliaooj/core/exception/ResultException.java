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

package com.wuluknut.tongliaooj.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 结果异常类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Getter
public class ResultException extends RuntimeException {

    private final HttpStatus status;

    public ResultException() {
        this("操作失败！");
    }

    public ResultException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public ResultException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
