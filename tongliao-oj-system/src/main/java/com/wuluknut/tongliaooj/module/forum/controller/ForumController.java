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

package com.wuluknut.tongliaooj.module.forum.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.wuluknut.tongliaooj.core.annotation.LogStore;
import com.wuluknut.tongliaooj.module.forum.model.CommentDO;
import com.wuluknut.tongliaooj.module.forum.model.DiscussionDO;
import com.wuluknut.tongliaooj.module.forum.service.CommentService;
import com.wuluknut.tongliaooj.module.forum.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.wuluknut.tongliaooj.module.forum.model.table.CommentDOTableDef.COMMENT_D_O;
import static com.wuluknut.tongliaooj.module.forum.model.table.DiscussionDOTableDef.DISCUSSION_D_O;

/**
 * 讨论控制器
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {

    private final CommentService commentService;

    private final DiscussionService discussionService;

    @GetMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    public Page<CommentDO> list(@Valid @NotNull Long parentId, @RequestParam(defaultValue = "1") Integer page) {
        return commentService.page(new Page<>(page, 20), QueryWrapper.create().where(COMMENT_D_O.PARENT_ID.eq(parentId)).orderBy(COMMENT_D_O.CREATE_TIME.asc()));
    }

    @LogStore
    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    public void add(@Valid @NotNull Long parentId, @Valid @NotBlank String content) {
        commentService.save(CommentDO.builder().parentId(parentId).content(content).star(0).build());
    }

    @GetMapping("/discussion")
    @PreAuthorize("isAuthenticated()")
    public Page<DiscussionDO> list(@RequestParam(defaultValue = "1") Integer page, String keyword, @RequestParam(defaultValue = "false") Boolean me, Authentication authentication) {
        return discussionService.page(new Page<>(page, 20), QueryWrapper.create().where(DISCUSSION_D_O.CREATE_BY.eq(authentication.getName()).when(me)).and(DISCUSSION_D_O.TITLE.like("%" + keyword + "%").when(StringUtils.hasText(keyword))).orderBy(DISCUSSION_D_O.CREATE_TIME.desc()));
    }

    @LogStore
    @PostMapping("/discussion")
    @PreAuthorize("isAuthenticated()")
    public void add(@Valid @NotBlank String title, @Valid @NotBlank String content) {
        discussionService.save(DiscussionDO.builder().title(title).content(content).star(0).build());
    }

    @GetMapping("/discussion/info")
    @PreAuthorize("isAuthenticated()")
    public DiscussionDO info(@Valid @NotNull Long id) {
        return discussionService.getById(id);
    }

    @PostMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public void del(@Valid @NotNull Long id, @RequestParam(defaultValue = "false") Boolean is, Authentication authentication) {
        if (Boolean.TRUE.equals(is)) {
            commentService.remove(QueryWrapper.create().where(COMMENT_D_O.ID.eq(id).and(COMMENT_D_O.CREATE_BY.eq(authentication.getName()))));
        } else {
            if (discussionService.remove(QueryWrapper.create().where(DISCUSSION_D_O.ID.eq(id).and(DISCUSSION_D_O.CREATE_BY.eq(authentication.getName()))))) {
                commentService.remove(QueryWrapper.create().where(COMMENT_D_O.PARENT_ID.eq(id)));
            }
        }
    }

    @PostMapping("/star")
    @PreAuthorize("isAuthenticated()")
    public void star(@Valid @NotNull Long id, @RequestParam(defaultValue = "false") Boolean is) {
        if (Boolean.TRUE.equals(is)) {
            commentService.updateById(CommentDO.builder().id(id).star(commentService.getById(id).getStar() + 1).build());
        } else {
            discussionService.updateById(DiscussionDO.builder().id(id).star(discussionService.getById(id).getStar() + 1).build());
        }
    }
}
