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

package com.wuluknut.tongliaooj.module.judge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuluknut.tongliaooj.core.exception.ResultException;
import com.wuluknut.tongliaooj.module.judge.model.dto.JudgeDTO;
import com.wuluknut.tongliaooj.module.judge.properties.JudgeProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 代码执行服务类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JudgeService {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final JudgeProperties judgeProperties;

    private final ObjectMapper objectMapper;

    public JudgeDTO query(String token) {
        return request(HttpMethod.GET, token + "?base64_encoded=false&fields=status,stdout,stderr,token,time,memory", null);
    }

    @SneakyThrows
    public String submit(Integer lang, String code, String input) {
        return request(HttpMethod.POST, "", objectMapper.writeValueAsString(JudgeDTO.builder().lang(lang).code(code).input(input).build())).getToken();
    }

    private JudgeDTO request(HttpMethod method, String url, String body) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Map<String, String> map : judgeProperties.getHeader()) {
            headers.set(map.get("key"), map.get("value"));
        }

        try {
            ResponseEntity<JudgeDTO> exchange = REST_TEMPLATE.exchange(judgeProperties.getUrl() + url, method, new HttpEntity<>(body, headers), JudgeDTO.class);

            if (exchange.getStatusCode().equals(HttpStatus.OK) || exchange.getStatusCode().equals(HttpStatus.CREATED)) {
                return exchange.getBody();
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        throw new ResultException(HttpStatus.SERVICE_UNAVAILABLE, "代码执行请求失败");
    }
}
