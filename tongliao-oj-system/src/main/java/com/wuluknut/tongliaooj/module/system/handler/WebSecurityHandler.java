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

package com.wuluknut.tongliaooj.module.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuluknut.tongliaooj.core.ip2region.Ip2regionParser;
import com.wuluknut.tongliaooj.core.model.vo.ResultVO;
import com.wuluknut.tongliaooj.core.util.TokenUtils;
import com.wuluknut.tongliaooj.core.util.UserAgentUtils;
import com.wuluknut.tongliaooj.module.system.mapper.LogMapper;
import com.wuluknut.tongliaooj.module.system.model.entity.LogDO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Spring Security 认证处理器类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Component
@RequiredArgsConstructor
public class WebSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutHandler {

    private final StringRedisTemplate redisTemplate;

    private final Ip2regionParser ip2regionParser;

    private final ObjectMapper objectMapper;

    private final LogMapper logMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String token = TokenUtils.create(authentication.getName(), secret);

        redisTemplate.opsForValue().set(authentication.getName(), token, 7 * 24L, TimeUnit.HOURS);

        logMapper.insert(LogDO.builder().userAgent(UserAgentUtils.parser(request)).requestIp(ip2regionParser.parser(request)).time("0ms").url(request.getRequestURI()).param(objectMapper.writeValueAsString(request.getParameterMap())).exception("none").build());

        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(ResultVO.success(token)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(ResultVO.failure(HttpStatus.UNAUTHORIZED, getMassage(exception))));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            if (authentication != null && authentication.isAuthenticated()) {
                redisTemplate.delete(authentication.getName());

                writer.write(objectMapper.writeValueAsString(ResultVO.success()));
            } else {
                writer.write(objectMapper.writeValueAsString(ResultVO.failure(HttpStatus.UNAUTHORIZED, "您已经退出账号！")));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String getMassage(AuthenticationException exception) {
        String massage = "登录失败！";

        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            massage = "账号密码错误！";
        } else if (exception instanceof LockedException) {
            massage = "账号被锁定！请联系管理员处理。";
        } else if (exception instanceof DisabledException) {
            massage = "账号不可用！请联系管理员处理。";
        } else if (exception instanceof AccountExpiredException) {
            massage = "账号已过期！请联系管理员处理。";
        } else if (exception instanceof CredentialsExpiredException) {
            massage = "凭证已过期！请联系管理员处理。";
        }

        return massage;
    }
}
