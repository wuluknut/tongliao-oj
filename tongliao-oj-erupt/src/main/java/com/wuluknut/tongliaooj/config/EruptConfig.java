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

package com.wuluknut.tongliaooj.config;

import com.wuluknut.tongliaooj.model.code.Contest;
import com.wuluknut.tongliaooj.model.code.Problem;
import com.wuluknut.tongliaooj.model.forum.Comment;
import com.wuluknut.tongliaooj.model.forum.Discussion;
import com.wuluknut.tongliaooj.model.system.Log;
import com.wuluknut.tongliaooj.model.system.Team;
import com.wuluknut.tongliaooj.model.system.User;
import org.springframework.context.annotation.Configuration;
import xyz.erupt.core.constant.MenuStatus;
import xyz.erupt.core.constant.MenuTypeEnum;
import xyz.erupt.core.module.EruptModule;
import xyz.erupt.core.module.EruptModuleInvoke;
import xyz.erupt.core.module.MetaMenu;
import xyz.erupt.core.module.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Erupt Framework 配置类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Configuration
public class EruptConfig implements EruptModule {


    static {
        EruptModuleInvoke.addEruptModule(EruptConfig.class);
    }

    @Override
    public ModuleInfo info() {
        return ModuleInfo.builder().name("web-oj").build();
    }

    @Override
    public List<MetaMenu> initMenus() {
        List<MetaMenu> menus = new ArrayList<>();

        menus.add(MetaMenu.createRootMenu("$system", "前台系统", "fa fa-tachometer", 2));
        menus.add(MetaMenu.createRootMenu("$forum", "社区管理", "fa fa-comments", 3));
        menus.add(MetaMenu.createRootMenu("$code", "评测管理", "fa fa-codepen", 4));

        menus.add(MetaMenu.createEruptClassMenu(Log.class, menus.get(0), 0));
        menus.add(MetaMenu.createEruptClassMenu(Team.class, menus.get(0), 10, MenuTypeEnum.TREE));
        menus.add(MetaMenu.createEruptClassMenu(User.class, menus.get(0), 20));

        menus.add(MetaMenu.createEruptClassMenu(Comment.class, menus.get(1), 10, MenuStatus.HIDE));
        menus.add(MetaMenu.createEruptClassMenu(Discussion.class, menus.get(1), 0));

        menus.add(MetaMenu.createEruptClassMenu(Contest.class, menus.get(2), 10));
        menus.add(MetaMenu.createEruptClassMenu(Problem.class, menus.get(2), 0));

        return menus;
    }
}
