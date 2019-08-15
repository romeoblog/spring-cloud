package com.cloud.example.search;

import com.cloud.example.common.utils.JacksonUtils;
import com.cloud.example.search.utils.ClassPathResourceReader;
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
