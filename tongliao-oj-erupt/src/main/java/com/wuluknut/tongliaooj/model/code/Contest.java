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

package com.wuluknut.tongliaooj.model.code;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.MetaModelVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 竞赛管理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "tbl_contest")
@Erupt(
        name = "竞赛管理"
)
@Entity
@Getter
@Setter
public class Contest extends MetaModelVo {

    @EruptField(
            views = @View(title = "竞赛名称"),
            edit = @Edit(title = "竞赛名称", notNull = true, search = @Search(vague = true))
    )
    @Column(nullable = false)
    private String title;

    @EruptField(
            views = @View(title = "结束日期", sortable = true),
            edit = @Edit(
                    title = "结束日期",
                    type = EditType.DATE, notNull = true,
                    dateType = @DateType(
                            type = DateType.Type.DATE_TIME
                    )
            )
    )
    @Column(nullable = false)
    private LocalDateTime stopTime;
}
