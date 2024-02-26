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

package com.wuluknut.tongliaooj.core.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {

    public static final String EXP_TIME_KEY = "exp_time";
    public static final String LOGIN_TIME_KEY = "login_time";
    public static final String USERNAME_KEY = "username";

    public static String create(String username, String secret) {
        HashMap<String, Object> payload = new HashMap<>();

        payload.put(EXP_TIME_KEY, LocalDateTime.now().plusDays(5).toString());
        payload.put(LOGIN_TIME_KEY, LocalDateTime.now().toString());
        payload.put(USERNAME_KEY, username);

        try {
            JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), new Payload(payload));

            jwsObject.sign(new MACSigner(secret));

            return jwsObject.serialize();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Map<String, Object> verify(String token, String secret) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);

            if (jwsObject.verify(new MACVerifier(secret))) {
                return jwsObject.getPayload().toJSONObject();
            }

            return Collections.emptyMap();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
