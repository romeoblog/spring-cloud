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
package com.cloud.mesh.search;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.utils.ClassPathResourceReader;
import org.junit.Test;

/**
 * ClassPathResourceReaderTest
 *
 * @author Benji
 * @date 2019-08-15
 */
public class ClassPathResourceReaderTest {

    @Test
    public void readFile() {
        String content = new ClassPathResourceReader("mappings/default-mapping.json").getContent();

        System.out.println(content);

        String toJson = JacksonUtils.toJson(content);

        System.out.println(toJson);
    }

}
