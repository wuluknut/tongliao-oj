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

package com.wuluknut.tongliaooj.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuluknut.tongliaooj.core.exception.ResultException;
import com.wuluknut.tongliaooj.core.model.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

/**
 * 全局异常处理器类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object>, ErrorViewResolver {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseBody.class;

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body instanceof String || Objects.equals(Objects.requireNonNull(returnType.getMethod()).getReturnType(), String.class)) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return objectMapper.writeValueAsString(ResultVO.success(null, body.toString()));
        }
        if (body instanceof ResultVO<?> || body instanceof byte[]) {
            return body;
        }
        if (body != null) {
            return ResultVO.success(body);
        }

        return ResultVO.success();
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        return new ModelAndView("redirect:/#/err");
    }

    @ExceptionHandler(ResultException.class)
    public ResultVO<HttpStatus> result(ResultException e) {
        return ResultVO.failure(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ValidationException.class})
    public ResultVO<HttpStatus> bind() {
        return ResultVO.failure("错误的请求！");
    }

    @ExceptionHandler({DataAccessException.class, DataIntegrityViolationException.class})
    public ResultVO<HttpStatus> data() {
        return ResultVO.failure("数据操作出错！");
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ResultVO<HttpStatus> auth() {
        return ResultVO.failure(HttpStatus.FORBIDDEN, "您没有访问权限！");
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<HttpStatus> exception(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return ResultVO.failure(HttpStatus.INTERNAL_SERVER_ERROR, "程序参数异常！");
        }

        return ResultVO.failure(HttpStatus.INTERNAL_SERVER_ERROR, "服务器异常！");
    }
}
