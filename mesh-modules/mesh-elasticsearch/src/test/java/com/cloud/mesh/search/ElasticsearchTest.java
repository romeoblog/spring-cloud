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

import com.alibaba.fastjson.JSONObject;
import com.cloud.mesh.common.utils.DateUtils;
import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.utils.ClassPathResourceReader;
import com.cloud.mesh.search.utils.ElasticsearchPage;
import com.cloud.mesh.search.utils.ElasticsearchUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
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

    private final String INDEX_NAME = "test";

    // ElasticsearchStatusException[Elasticsearch exception [type=invalid_index_name_exception,
    // reason=Invalid index name [indexTest], must be lowercase]
    @Test
    public void createIndexTest() throws Exception {
        boolean createIndex = ElasticsearchUtils.createIndex(INDEX_NAME);

        System.out.println(createIndex);
    }

    @Test
    public void createMappingTest() throws Exception {
        Map<String, Map<String, String>> proNames = Maps.newHashMap();

        Map<String, String> messageFields = Maps.newHashMap();
        messageFields.put("type", "keyword");
        //messageFields.put("analyzer", "ik_max_word");
        messageFields.put("index", "true");

        proNames.put("name", messageFields);

        boolean createMapping = ElasticsearchUtils.createMapping(INDEX_NAME, proNames);

        System.out.println(createMapping);
    }

    @Test
    public void createMappingJsonTest() throws Exception {

        // boolean createMapping = ElasticsearchUtils.createMapping(INDEX_NAME);

        String fileName = "mappings/" + INDEX_NAME + "-mapping.json";

        String mappingJson = new ClassPathResourceReader(fileName).getContent();

        boolean createMapping = ElasticsearchUtils.createMapping(INDEX_NAME, mappingJson);

        System.out.println(createMapping);
    }

    @Test
    public void deleteIndexTest() throws Exception {
        boolean deleteIndex = ElasticsearchUtils.deleteIndex(INDEX_NAME);

        System.out.println(deleteIndex);
    }

    @Test
    public void existsIndexTest() throws Exception {

        boolean existsIndex = ElasticsearchUtils.existsIndex(INDEX_NAME);

        System.out.println(existsIndex);

    }

    @Test
    public void createDocumentTest() throws Exception {

        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        String id = ElasticsearchUtils.createDocument(jsonObject, INDEX_NAME, "1");

        System.out.println(id);

    }

    @Test
    public void createDocumentAutoTest() throws Exception {

        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        String id = ElasticsearchUtils.createDocument(jsonObject, INDEX_NAME);

        System.out.println(id);

    }

    @Test
    public void updateDocumentByIdTest() throws Exception {

        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        ElasticsearchUtils.updateDocumentById(jsonObject, INDEX_NAME, "1");

    }

    @Test
    public void deleteDocumentByIdTest() throws Exception {
        ElasticsearchUtils.deleteDocumentById(INDEX_NAME, "334AB0FDEBEA4B439FDC93C9C3E6A7D6");
    }

    @Test
    public void batchCreateDocumentTest() throws Exception {

        List<JSONObject> list = Lists.newArrayList();

        String jsonString1 = "{" +
                "\"user\":\"kimchy1\"," +
                "\"postDate\":\"2019-08-27\"," +
                "\"message\":\"trying out Elasticsearch 1\"" +
                "}";
        JSONObject jsonObject1 = JSONObject.parseObject(jsonString1);

        String jsonString2 = "{" +
                "\"user\":\"kimchy2\"," +
                "\"postDate\":\"2019-08-28\"," +
                "\"message\":\"trying out Elasticsearch 2\"" +
                "}";
        JSONObject jsonObject2 = JSONObject.parseObject(jsonString2);

        String jsonString3 = "{" +
                "\"user\":\"kimchy3\"," +
                "\"postDate\":\"2019-08-29\"," +
                "\"message\":\"trying out Elasticsearch 3\"" +
                "}";
        JSONObject jsonObject3 = JSONObject.parseObject(jsonString3);

        String jsonString4 = "{" +
                "\"user\":\"kimchy4\"," +
                "\"postDate\":\"2019-08-30\"," +
                "\"message\":\"trying out Elasticsearch 4\"" +
                "}";
        JSONObject jsonObject4 = JSONObject.parseObject(jsonString4);

        list.add(jsonObject1);
        list.add(jsonObject2);
        list.add(jsonObject3);
        list.add(jsonObject4);

        ElasticsearchUtils.batchCreateDocument(list, INDEX_NAME, "2", "3", "4", "5");

    }


    @Test
    public void searchListDocumentTest() throws Exception {

        String start = "20190827";
        String end = "20190829";

        ///List<Map<String, Object>> searchDocument = ElasticsearchUtils.searchListDocument(INDEX_NAME, "postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), null, null, null, false, null, null);

        // List<Map<String, Object>> searchDocument = ElasticsearchUtils.searchListDocument(INDEX_NAME, "postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), null, null, null, true, null, "message=Elasticsearch 2");

        //List<Map<String, Object>> searchDocument = ElasticsearchUtils.searchListDocument(INDEX_NAME, "postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), null, "postDate,message", null, true, null, "message=Elasticsearch");

        //List<Map<String, Object>> searchDocument = ElasticsearchUtils.searchListDocument(INDEX_NAME, "postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), 1, "postDate,message", "_id", true, null, "message=Elasticsearch");

        List<Map<String, Object>> searchDocument = ElasticsearchUtils.searchListDocument(INDEX_NAME, "postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), 10, "postDate,message", "_id", false, "message", "message=Elasticsearch");

        System.out.println("======================");
        System.out.println(JacksonUtils.toJson(searchDocument));
        System.out.println("======================");

    }

    @Test
    public void searchPageDocumentTest() throws Exception {

        String start = "20190827";
        String end = "20190829";

        ElasticsearchPage searchDocument = ElasticsearchUtils.searchPageDocument(INDEX_NAME, 1,10,"postDate", DateUtils.getDateByDay(start).getTime(), DateUtils.getDateByDay(end).getTime(), "postDate,message", "_id", false, "message", "message=Elasticsearch");

        System.out.println("======================");
        System.out.println(JacksonUtils.toJson(searchDocument));
        System.out.println("======================");

    }

    @Test
    public void test() {
        String ss = "";

        System.out.println(ss.split(",")[0]);
        System.out.println(StringUtils.split(ss,",")[0]);
    }


}
