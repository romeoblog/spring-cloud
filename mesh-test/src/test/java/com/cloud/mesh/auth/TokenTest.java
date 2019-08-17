/*
 *  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cloud.mesh.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.Date;

/**
 * token test
 *
 * @author Benji
 * @date 2019-06-08
 */
public class TokenTest {

    @Test
    public void contextLoads() {
        //填写token
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFwcCJdLCJleHAiOjE1NjAwMjM4NjAsInVzZXJOYW1lIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJTeXN0ZW1Vc2VyIiwiU3lzdGVtVXNlckluc2VydCIsIlN5c3RlbVVzZXJEZWxldGUiLCJTeXN0ZW1Vc2VyVXBkYXRlIiwiU3lzdGVtVXNlclZpZXciLCJTeXN0ZW0iXSwianRpIjoiZjZkNGM5YzgtMWE5My00NzY2LTkyMzktMTVmNDMzMzRmNjcwIiwiY2xpZW50X2lkIjoiYW5kcm9pZCJ9.h2E56t1IJC_-rm9xS9cPkDho2vFhxA854WKnhclfSBGvArz47YwwCO2F_k9QA_CoImjndD_TC7QcJE5YrNe5oM_Qj9yXKudMIqE-kUbqqZT2x3NcV7-5YmJY5ebvj7nONhqfUKPt7LnocbTgOCAcpls1h6lBgikx3hU_vnRMo92oxgjKn1RIDGjPlDq3lG8Iru0NHVkZ6Cm8EVFSVgAzmf7n6EOaujp0h0SAANRFVYvX5acBKZlH1oFKsPbUlRxDwLQ7rPSnPRpz6-0V94MTq_Z9shUUTM-sAd5r4PwFLxB9e-_YOKGPZS1EzKOMV6ZgNWIMNlaTN-0g5-gHFl21Sw";

        // {"alg":"RS256","typ":"JWT"} {"user_name":"admin","scope":["app"],"exp":1559969400,"userName":"admin","authorities":[{"authority":"System"},{"authority":"SystemUser"},{"authority":"SystemUserDelete"},{"authority":"SystemUserInsert"},{"authority":"SystemUserUpdate"},{"authority":"SystemUserView"}],"jti":"45e4ca30-b8c8-4c70-9318-8934cfbfe969","client_id":"android"} [256 crypto bytes]
        // {"alg":"RS256","typ":"JWT"} {"exp":1559967686,"user_name":"admin","authorities":["SystemUser","SystemUserInsert","SystemUserDelete","SystemUserUpdate","SystemUserView","System"],"jti":"b7d8515e-d9c2-4561-b154-fe7d2a5e97a2","client_id":"android","scope":["app"]} [256 crypto bytes]
        Jwt jwts = JwtHelper.decode(token);

        DecodedJWT jwt = JWT.decode(token);

        // Algorithm ("alg")
        String algorithm = jwt.getAlgorithm();

        // Type ("typ")
        String type = jwt.getType();

        // Content Type ("cty")
        String contentType = jwt.getContentType();

        // Key Id ("kid")
        String keyId = jwt.getKeyId();

        // Expiration Time ("exp")
        Date expiresAt = jwt.getExpiresAt();

        // JWT ID ("jti")
        String id = jwt.getId();

        System.err.println(jwts.toString());
        System.err.println(algorithm);
        System.err.println(type);
        System.err.println(contentType);
        System.err.println(keyId);
        System.err.println(expiresAt);
        System.err.println(id);

    }
}
