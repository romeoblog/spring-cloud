package com.cloud.example.search;

import com.cloud.example.search.utils.ElasticsearchUtils;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * ElasticsearchTest
 *
 * @author Benji
 * @date 2019-08-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ElasticsearchTest {

    // ElasticsearchStatusException[Elasticsearch exception [type=invalid_index_name_exception,
    // reason=Invalid index name [indexTest], must be lowercase]
    @Test
    public void createIndexTest() throws Exception {
        boolean createIndex = ElasticsearchUtils.createIndex("index_test");

        System.out.println(createIndex);
    }

    @Test
    public void createMappingTest() throws Exception {
        Map<String, Map<String, String>> proNames = Maps.newHashMap();

        Map<String, String> messageFields = Maps.newHashMap();
        messageFields.put("type", "text");
        messageFields.put("index", "not_analyzed");

        proNames.put("message", messageFields);

        boolean createMapping = ElasticsearchUtils.createMapping("index_test", proNames);

        System.out.println(createMapping);
    }

    @Test
    public void deleteIndexTest() throws Exception {
        boolean deleteIndex = ElasticsearchUtils.deleteIndex("index_test");

        System.out.println(deleteIndex);
    }

    @Test
    public void existsIndexTest() throws Exception {

        boolean existsIndex = ElasticsearchUtils.existsIndex("twitter");

        System.out.println(existsIndex);

    }


}
