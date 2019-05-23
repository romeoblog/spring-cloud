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
package com.dg.mall.auth;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testTest {

    @Test
    public void getPass() {
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);
        config.setPublicKey("-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXVn4HyizPBDgqVKhkSljehE95qD4kk1zwT2NZ\n" +
                "khOluNuiLxuh2sQmfcKm9uCSBaddaQRIGQrBW6rJrGodkOkgiNjhnuBpBaT5hT4tX/y3mga8ZahT\n" +
                "341fFUwtXl1DZ6NJ76yX693XO3FjMGaAslggLW1HEUEdvUiQff6ZkggnWwIDAQAB\n" +
                "-----END PUBLIC KEY-----");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println(name);
        System.out.println(password);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String admin = bCryptPasswordEncoder.encode("nacos");

        System.out.println(admin);

    }
}