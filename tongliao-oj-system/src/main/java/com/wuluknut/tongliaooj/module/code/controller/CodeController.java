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
import com.wuluknut.tongliaooj.module.judge.model.ScoreDO;
import com.wuluknut.tongliaooj.module.judge.model.dto.JudgeDTO;
import com.wuluknut.tongliaooj.module.judge.service.JudgeService;
import com.wuluknut.tongliaooj.module.judge.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.wuluknut.tongliaooj.module.code.model.table.ContestDOTableDef.CONTEST_D_O;
import static com.wuluknut.tongliaooj.module.code.model.table.ProblemDOTableDef.PROBLEM_D_O;
import static com.wuluknut.tongliaooj.module.judge.model.table.ScoreDOTableDef.SCORE_D_O;

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

    private final JudgeService judgeService;

    private final ContestService contestService;

    private final ProblemService problemService;

    private final ScoreService scoreService;

    @GetMapping("/contest")
    @PreAuthorize("isAuthenticated()")
    public Page<ContestDO> list(@RequestParam(defaultValue = "1") Integer page) {
        return contestService.page(new Page<>(page, 10), QueryWrapper.create().orderBy(CONTEST_D_O.STOP_TIME.desc()));
    }

    @GetMapping("/contest/info")
    @PreAuthorize("isAuthenticated()")
    public List<ProblemDO> info(@Valid @NotNull Long id, Authentication authentication) {
        if (contestService.getById(id).getStopTime().isBefore(LocalDateTime.now()) || scoreService.getOne(QueryWrapper.create().where(SCORE_D_O.CREATE_BY.eq(authentication.getName())).and(SCORE_D_O.CID.eq(id))) != null) {
            return Collections.emptyList();
        }

        return problemService.findAllByContestId(id);
    }

    @GetMapping("/contest/score")
    @PreAuthorize("isAuthenticated()")
    public List<ScoreDO> score(@Valid @NotNull Long id) {
        return scoreService.list(QueryWrapper.create().where(SCORE_D_O.CID.eq(id)).orderBy(SCORE_D_O.RESULT.desc()));
    }

    @PostMapping("/contest/submit")
    @PreAuthorize("isAuthenticated()")
    public void submit(@Valid @NotNull Long id, @Valid @NotNull Long[] scores, Authentication authentication) {
        if (scoreService.getOne(QueryWrapper.create().where(SCORE_D_O.CREATE_BY.eq(authentication.getName())).and(SCORE_D_O.CID.eq(id))) == null) {
            scoreService.save(ScoreDO.builder().cid(id).status(3).code("none").time("none").memory("none").result(scoreService.listByIds(Arrays.asList(scores)).stream().mapToInt(ScoreDO::getResult).sum()).build());
        }
    }

    @GetMapping("/problem")
    @PreAuthorize("isAuthenticated()")
    public Page<ProblemDO> list(@RequestParam(defaultValue = "1") Integer page, String keyword) {
        return problemService.page(new Page<>(page, 10), QueryWrapper.create().where(PROBLEM_D_O.TITLE.like("%" + keyword + "%").when(StringUtils.hasText(keyword))));
    }

    @GetMapping("/problem/info")
    @PreAuthorize("isAuthenticated()")
    public ProblemDO info(@Valid @NotNull Long id) {
        return problemService.getById(id);
    }

    @PostMapping("/judge/submit")
    @PreAuthorize("isAuthenticated()")
    public String submit(@Valid @NotNull Long id, @Valid @NotBlank String code) {
        ProblemDO problem = problemService.getById(id);

        String token = judgeService.submit(problem.getLanguage(), code + problem.getTest(), "test");

        ScoreDO score = ScoreDO.builder().pid(problem.getId()).token(token).status(1).code(code).time("none").memory("none").result(0).build();

        scoreService.save(score);

        return token;
    }

    @GetMapping("/judge/query")
    @PreAuthorize("isAuthenticated()")
    public ScoreDO query(String token) {
        JudgeDTO query = judgeService.query(token);

        ScoreDO score = scoreService.getOne(QueryWrapper.create().where(SCORE_D_O.TOKEN.eq(token)));

        score.setStatus(query.getStatus().getId());
        score.setMemory(query.getMemory());
        score.setTime(query.getTime());

        if (query.getStatus().getId().equals(3)) {
            score.setResult(10000 - (int) (Float.parseFloat(query.getTime()) * 1000) - (Integer.parseInt(query.getMemory()) / 100));
        }

        scoreService.updateById(score);

        return score;
    }
}
