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

import com.wuluknut.tongliaooj.model.forum.proxy.DiscussionDataProxy;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Drill;
import xyz.erupt.annotation.sub_erupt.Link;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.MetaModelCreateVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 讨论管理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "tbl_discussion")
@Erupt(
        name = "讨论管理",
        dataProxy = DiscussionDataProxy.class,
        drills = {
                @Drill(title = "评论", link = @Link(linkErupt = Comment.class, joinColumn = "parentId"))
        },
        power = @Power(add = false, edit = false, viewDetails = false, export = true)
)
@Entity
@Getter
@Setter
public class Discussion extends MetaModelCreateVo {

    @EruptField(
            views = @View(title = "标题"),
            edit = @Edit(title = "标题", notNull = true, search = @Search(vague = true))
    )
    @Column(nullable = false)
    private String title;

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
