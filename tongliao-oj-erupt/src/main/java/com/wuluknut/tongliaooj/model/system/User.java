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

package com.wuluknut.tongliaooj.model.system;

import com.wuluknut.tongliaooj.model.system.proxy.UserDataProxy;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.MetaModelVo;

import javax.persistence.*;

/**
 * 学生管理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "sys_user")
@Erupt(
        name = "学生管理",
        dataProxy = UserDataProxy.class,
        linkTree = @LinkTree(field = "team"),
        power = @Power(importable = true)
)
@Entity
@Getter
@Setter
public class User extends MetaModelVo {

    @EruptField(
            views = @View(title = "用户名称", sortable = true),
            edit = @Edit(title = "用户名称", notNull = true, search = @Search(vague = true))
    )
    @Column(unique = true, nullable = false)
    private String username;

    @EruptField(
            views = @View(title = "学生姓名"),
            edit = @Edit(title = "学生姓名", notNull = true, search = @Search(vague = true))
    )
    @Column(nullable = false)
    private String nickname;

    @ManyToOne
    @EruptField(
            views = @View(title = "学生班级", column = "name"),
            edit = @Edit(
                    title = "学生班级",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parent.id")
            )
    )
    private Team team;

    @Transient
    @EruptField(
            edit = @Edit(title = "用户密码", desc = "添加用户时，空置密码将使用手机号码为默认密码。")
    )
    private String passwordView;

    @Column(nullable = false)
    private String password;

    @EruptField(
            views = @View(title = "手机号码"),
            edit = @Edit(title = "手机号码", notNull = true, search = @Search(vague = true))
    )
    @Column(nullable = false)
    private String phone;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(
                    title = "状态",
                    type = EditType.BOOLEAN, notNull = true,
                    boolType = @BoolType(trueText = "启用", falseText = "禁用")
            )
    )
    @Column(nullable = false)
    private Boolean status = true;
}
