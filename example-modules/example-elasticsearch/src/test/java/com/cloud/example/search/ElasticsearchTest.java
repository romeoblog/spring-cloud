package com.cloud.example.search;

import com.cloud.example.search.utils.ElasticsearchUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
