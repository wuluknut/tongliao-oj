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

package com.wuluknut.tongliaooj.module.judge.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 运行得分
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table("tbl_score")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class ScoreDO extends BaseEntity {

    private static final long serialVersionUID = 3266913427801067168L;

    private Long cid;
    private Long pid;

    private String token;

    /**
     * 运行状态
     */
    private Integer status;

    /**
     * 运行代码
     */
    private String code;

    /**
     * 运行耗时
     */
    private String time;

    /**
     * 内存占用
     */
    private String memory;


    /**
     * 运行成绩
     */
    private Integer result;

    @Column(isLogicDelete = true)
    private Boolean delFlag;
}
