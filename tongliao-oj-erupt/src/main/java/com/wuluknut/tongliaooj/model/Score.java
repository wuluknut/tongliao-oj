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

package com.wuluknut.tongliaooj.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.jpa.model.MetaModelCreateVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 运行得分
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Table(name = "tbl_score")
@SQLDelete(sql = "update tbl_score set del_flag = true where id = ?")
@Erupt(
        name = "运行得分",
        filter = @Filter("del_flag = false"),
        power = @Power(add = false, edit = false, viewDetails = false, export = true)
)
@Entity
@Getter
@Setter
public class Score extends MetaModelCreateVo {

    private Long cid;
    private Long pid;

    @EruptField(
            views = @View(title = "运行状态", width = "400"),
            edit = @Edit(
                    title = "运行状态",
                    type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "排队中，等待处理", value = "1"),
                                    @VL(label = "处理中，正在执行和测试", value = "2"),
                                    @VL(label = "通过，提交正确", value = "3"),
                                    @VL(label = "答案错误，输出不正确", value = "4"),
                                    @VL(label = "超出时间限制，执行时间过长", value = "5"),
                                    @VL(label = "编译错误，代码未能编译", value = "6"),
                                    @VL(label = "运行时错误，段错误", value = "7"),
                                    @VL(label = "运行时错误，超出文件大小限制", value = "8"),
                                    @VL(label = "运行时错误，浮点异常", value = "9"),
                                    @VL(label = "运行时错误，程序被终止", value = "10"),
                                    @VL(label = "运行时错误，非零退出代码", value = "11"),
                                    @VL(label = "运行时错误，其他未指定错误", value = "12"),
                                    @VL(label = "内部错误，裁判系统处理时出错", value = "13"),
                                    @VL(label = "执行格式错误，无法执行", value = "14"),
                            }
                    )
            )
    )
    @Column(nullable = false)
    private Integer status = 62;

    @EruptField(
            views = @View(title = "运行代码", width = "200", type = ViewType.CODE)
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @EruptField(
            views = @View(title = "运行耗时", desc = "秒")
    )
    @Column(nullable = false)
    private String time;

    @EruptField(
            views = @View(title = "内存占用")
    )
    @Column(nullable = false)
    private String memory;

    @EruptField(
            views = @View(title = "运行成绩")
    )
    @Column(nullable = false)
    private Integer result;

    @Column(nullable = false)
    private Boolean delFlag = false;
}
