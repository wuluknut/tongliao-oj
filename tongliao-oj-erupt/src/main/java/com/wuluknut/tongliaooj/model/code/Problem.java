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
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.MetaModelVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 问题管理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "tbl_problem")
@Erupt(
        name = "问题管理",
        power = @Power(importable = true)
)
@Entity
@Getter
@Setter
public class Problem extends MetaModelVo {

    @EruptField(
            views = @View(title = "题目标题", width = "400"),
            edit = @Edit(title = "题目标题", notNull = true, search = @Search(vague = true))
    )
    @Column(nullable = false)
    private String title = "倒叙字符串";

    @EruptField(
            views = @View(title = "题目语言", width = "100"),
            edit = @Edit(
                    title = "题目语言",
                    type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "C++", value = "54"),
                                    @VL(label = "Java", value = "62"),
                                    @VL(label = "JavaScript", value = "63"),
                                    @VL(label = "Python", value = "71"),
                                    @VL(label = "SQL", value = "82")
                            }
                    )
            )
    )
    @Column(nullable = false)
    private Integer language = 62;

    @EruptField(
            views = @View(title = "题目难度", width = "100"),
            edit = @Edit(
                    title = "题目难度",
                    type = EditType.RATE, notNull = true,
                    rateType = @RateType(allowHalf = true, count = 10)
            )
    )
    @Column(nullable = false)
    private Float rate = 0f;

    @EruptField(
            views = @View(title = "函数声明", width = "400"),
            edit = @Edit(
                    title = "函数声明",
                    type = EditType.INPUT, notNull = true,
                    inputType = @InputType(fullSpan = true)
            )
    )
    @Column(nullable = false)
    private String declare = "public static String execute(String str)";

    @EruptField(
            views = @View(title = "测试校验", width = "200", type = ViewType.CODE),
            edit = @Edit(
                    title = "测试校验",
                    type = EditType.CODE_EDITOR, notNull = true
            )
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String test = "// 在面向对象的语言中，声明的函数属于 Solution 类\npublic class Main {\n\tpublic static void main(String[] args) {\n\t\tif (!Solution.execute(\"test\").equals(\"test\")) {\n\t\t\tthrow new IllegalArgumentException(\"Failure\");\n\t\t}\n\t}\n}";

    @EruptField(
            views = @View(title = "题目描述", width = "200", type = ViewType.HTML),
            edit = @Edit(
                    title = "题目描述",
                    type = EditType.HTML_EDITOR, notNull = true,
                    htmlEditorType = @HtmlEditorType(HtmlEditorType.Type.UEDITOR)
            )
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question = "<p>给定一个字符串，请将它倒叙返回。</p><p><br/></p><p>示例：<br/></p><p>&nbsp;&nbsp;&nbsp;&nbsp;输入：tset<br/></p><p>&nbsp;&nbsp;&nbsp;&nbsp;返回：test</p><p><br/></p><p>提示：</p><p>&nbsp;&nbsp;&nbsp;&nbsp;可以使用 JDK 自带工具类，但请自行导入。OpenJDK 13.0.1</p><p><br/></p>";
}
