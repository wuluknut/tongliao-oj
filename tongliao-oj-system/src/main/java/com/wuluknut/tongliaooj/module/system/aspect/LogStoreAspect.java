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

package com.wuluknut.tongliaooj.module.system.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuluknut.tongliaooj.core.ip2region.Ip2regionParser;
import com.wuluknut.tongliaooj.core.util.UserAgentUtils;
import com.wuluknut.tongliaooj.module.system.mapper.LogMapper;
import com.wuluknut.tongliaooj.module.system.model.entity.LogDO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 日志存储切面类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LogStoreAspect {

    private static final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    private final Ip2regionParser ip2regionParser;

    private final ObjectMapper objectMapper;

    private final LogMapper logMapper;

    @Pointcut("@annotation(com.wuluknut.tongliaooj.core.annotation.LogStore)")
    private void logStorePointcut() {
    }

    @Around("logStorePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        currentTime.set(System.currentTimeMillis());

        Object result = joinPoint.proceed();

        store(System.currentTimeMillis() - currentTime.get(), null);
        currentTime.remove();

        return result;
    }

    @AfterThrowing(pointcut = "logStorePointcut()", throwing = "throwable")
    public void logAfterThrowing(Throwable throwable) {
        store(System.currentTimeMillis() - currentTime.get(), throwable);
        currentTime.remove();
    }

    @SneakyThrows
    private void store(Long time, Throwable throwable) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        logMapper.insert(LogDO.builder().userAgent(UserAgentUtils.parser(request)).requestIp(ip2regionParser.parser(request)).time(time + "ms").url(request.getRequestURI()).param(objectMapper.writeValueAsString(request.getParameterMap())).exception(getExceptionStackTrace(throwable)).build());
    }

    private String getExceptionStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "none";
        }

        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);

            return sw.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
