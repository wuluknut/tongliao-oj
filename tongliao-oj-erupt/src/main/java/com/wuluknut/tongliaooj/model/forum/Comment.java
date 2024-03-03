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

package com.wuluknut.tongliaooj.model.forum;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.jpa.model.MetaModelCreateVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 评论管理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "tbl_comment")
@Erupt(
        name = "评论管理",
        power = @Power(add = false, edit = false, viewDetails = false, export = true)
)
@Entity
@Getter
@Setter
public class Comment extends MetaModelCreateVo {

    @Column(nullable = false)
    private Long parentId;

    @EruptField(
            views = @View(title = "内容", width = "200", type = ViewType.HTML)
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @EruptField(
            views = @View(title = "点赞数", width = "100")
    )
    private Integer star;
}
