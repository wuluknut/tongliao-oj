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

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.jpa.model.MetaModelCreateVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 访问日志
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "sys_log")
@Erupt(
        name = "操作日志",
        power = @Power(add = false, edit = false, viewDetails = false, export = true)
)
@Entity
@Getter
@Setter
public class Log extends MetaModelCreateVo {

    @EruptField(
            views = @View(title = "请求来源")
    )
    @Column(nullable = false)
    private String requestIp;

    @EruptField(
            views = @View(title = "请求设备")
    )
    @Column(nullable = false)
    private String userAgent;

    @EruptField(
            views = @View(title = "请求耗时", desc = "毫秒")
    )
    @Column(nullable = false)
    private String time;

    @EruptField(
            views = @View(title = "请求路径")
    )
    @Column(nullable = false)
    private String url;

    @EruptField(
            views = @View(title = "请求参数", type = ViewType.CODE),
            edit = @Edit(title = "请求参数", type = EditType.CODE_EDITOR, codeEditType = @CodeEditorType(language = "json"))
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String param;

    @EruptField(
            views = @View(title = "错误信息", type = ViewType.CODE)
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String exception;
}
