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

package com.wuluknut.tongliaooj.model.forum.proxy;

import com.wuluknut.tongliaooj.model.forum.Discussion;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;

/**
 * 讨论管理数据代理
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Service
@RequiredArgsConstructor
public class DiscussionDataProxy implements DataProxy<Discussion> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void beforeDelete(Discussion discussion) {
        jdbcTemplate.update("delete from tbl_comment where parent_id = " + discussion.getId());
    }
}
