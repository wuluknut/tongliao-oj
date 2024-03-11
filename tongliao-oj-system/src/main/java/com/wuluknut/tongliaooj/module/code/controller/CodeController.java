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

package com.wuluknut.tongliaooj.module.code.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.wuluknut.tongliaooj.module.code.model.ContestDO;
import com.wuluknut.tongliaooj.module.code.model.ProblemDO;
import com.wuluknut.tongliaooj.module.code.service.ContestService;
import com.wuluknut.tongliaooj.module.code.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.wuluknut.tongliaooj.module.code.model.table.ContestDOTableDef.CONTEST_D_O;
import static com.wuluknut.tongliaooj.module.forum.model.table.DiscussionDOTableDef.DISCUSSION_D_O;

/**
 * 评测控制类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeController {

    private final ContestService contestService;

    private final ProblemService problemService;

    @GetMapping("/contest")
    @PreAuthorize("isAuthenticated()")
    public Page<ContestDO> list(@RequestParam(defaultValue = "1") Integer page) {
        return contestService.page(new Page<>(page, 10), QueryWrapper.create().orderBy(CONTEST_D_O.STOP_TIME.desc()));
    }

    @GetMapping("/problem")
    @PreAuthorize("isAuthenticated()")
    public Page<ProblemDO> list(@RequestParam(defaultValue = "1") Integer page, String keyword) {
        return problemService.page(new Page<>(page, 10), QueryWrapper.create().where(DISCUSSION_D_O.TITLE.like("%" + keyword + "%").when(StringUtils.hasText(keyword))));
    }

    @GetMapping("/problem/info")
    @PreAuthorize("isAuthenticated()")
    public ProblemDO info(@Valid @NotNull Long id) {
        return problemService.getById(id);
    }
}
