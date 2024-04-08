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

package com.wuluknut.tongliaooj.module.code.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.wuluknut.tongliaooj.module.code.model.ProblemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问题 Mapper 接口
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Mapper
public interface ProblemMapper extends BaseMapper<ProblemDO> {

    List<ProblemDO> listByContestId(@Param("id") Long id);
}
