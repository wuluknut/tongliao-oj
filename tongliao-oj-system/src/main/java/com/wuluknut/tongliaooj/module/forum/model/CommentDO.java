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

package com.wuluknut.tongliaooj.module.forum.model;

import com.mybatisflex.annotation.Table;
import com.wuluknut.tongliaooj.core.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 评论实体类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table("tbl_comment")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class CommentDO extends BaseEntity {

    private static final long serialVersionUID = 5041962524434312429L;

    /**
     * 讨论 ID
     */
    private Long parentId;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer star;
}
