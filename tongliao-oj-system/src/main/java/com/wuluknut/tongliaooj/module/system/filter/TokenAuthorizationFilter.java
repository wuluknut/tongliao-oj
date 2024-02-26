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

package com.wuluknut.tongliaooj.module.system.filter;

import com.mybatisflex.core.query.QueryWrapper;
import com.wuluknut.tongliaooj.core.util.TokenUtils;
import com.wuluknut.tongliaooj.module.system.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.wuluknut.tongliaooj.module.system.model.entity.table.UserDOTableDef.USER_D_O;

/**
 * JWT 授权过滤器类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Component
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String AUTH_HEADER_KEY = "Authorization";

    private static final String REFRESH_HEADER_KEY = "refresh";

    private final StringRedisTemplate redisTemplate;

    private final UserMapper userMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader(AUTH_HEADER_KEY);

        try {
            if (auth != null && auth.startsWith(TOKEN_PREFIX)) {
                String token = auth.substring(7);

                Map<String, Object> payload = TokenUtils.verify(token, secret);

                if (!payload.isEmpty()) {
                    String expTime = payload.get(TokenUtils.EXP_TIME_KEY).toString();
                    String username = payload.get(TokenUtils.USERNAME_KEY).toString();

                    UserDetails user = userMapper.selectOneByQuery(QueryWrapper.create().where(USER_D_O.USERNAME.eq(username)));

                    if (LocalDateTime.parse(expTime).isBefore(LocalDateTime.now())) {
                        String refresh = TokenUtils.create(username, secret);

                        redisTemplate.opsForValue().set(username, refresh, 7 * 24L, TimeUnit.HOURS);

                        response.setHeader(REFRESH_HEADER_KEY, refresh);
                    }

                    if (token.equals(redisTemplate.opsForValue().get(username))) {
                        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
                        authenticated.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticated);
                    }
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
