package com.cloud.example.auth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk5Njc2ODYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiU3lzdGVtVXNlciIsIlN5c3RlbVVzZXJJbnNlcnQiLCJTeXN0ZW1Vc2VyRGVsZXRlIiwiU3lzdGVtVXNlclVwZGF0ZSIsIlN5c3RlbVVzZXJWaWV3IiwiU3lzdGVtIl0sImp0aSI6ImI3ZDg1MTVlLWQ5YzItNDU2MS1iMTU0LWZlN2QyYTVlOTdhMiIsImNsaWVudF9pZCI6ImFuZHJvaWQiLCJzY29wZSI6WyJhcHAiXX0.jVEzqm7hije2OA8Q5Dadnnp4GUKZg7GOKjep_y0OkoOp8CZOW3cdaCrtEH4dD02PCrcXFztQfvYbnUoCJOP4WKnGJPXH14L4xt4Y_d5ywfu5ytVmdXRjJpYSSgOVvVcVQJb7Zi18SCYfOlU3-bT_mnnGvdqWDeXy2NPIu9UA0pDpJf-JHQFxmQ8af65EghhABKlE2Bj6dy1djZO3TE3NVy4NJ0HZCgB9n8gTVW7Bep-0bMaJ5dqfv_DSlEeQwUcNY2zgsY5LxtsUp92VMWtsS28SbYJpmVRbkkMjfYapNcP6oa8zLO9gU2jbv7VP7x57s3NDa3hW179dauHwiQQPuA";

        // {"alg":"RS256","typ":"JWT"} {"user_name":"admin","scope":["app"],"exp":1559969400,"userName":"admin","authorities":[{"authority":"System"},{"authority":"SystemUser"},{"authority":"SystemUserDelete"},{"authority":"SystemUserInsert"},{"authority":"SystemUserUpdate"},{"authority":"SystemUserView"}],"jti":"45e4ca30-b8c8-4c70-9318-8934cfbfe969","client_id":"android"} [256 crypto bytes]
        // {"alg":"RS256","typ":"JWT"} {"exp":1559967686,"user_name":"admin","authorities":["SystemUser","SystemUserInsert","SystemUserDelete","SystemUserUpdate","SystemUserView","System"],"jti":"b7d8515e-d9c2-4561-b154-fe7d2a5e97a2","client_id":"android","scope":["app"]} [256 crypto bytes]
        Jwt jwt = JwtHelper.decode(token);
        System.err.println(jwt.toString());
    }
}
