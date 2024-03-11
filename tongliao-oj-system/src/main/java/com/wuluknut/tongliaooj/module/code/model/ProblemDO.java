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

package com.wuluknut.tongliaooj.module.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Table;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 问题实体类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table("tbl_problem")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class ProblemDO extends BaseEntity {

    private static final long serialVersionUID = 3266913427801067168L;

    /**
     * 题目难度
     */
    private Float rate;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 函数声明
     */
    private String declare;

    /**
     * 题目语言
     */
    private Integer language;

    /**
     * 题目描述
     */
    private String question;

    /**
     * 测试校验
     */
    @JsonIgnore
    private String test;
}
