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
package com.cloud.example.search.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.example.common.utils.JacksonUtils;
import com.cloud.example.core.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A wrapper for the ElasticsearchUtils that provides methods for accessing the Indices API.
 *
 * @author Benji
 * @date 2019-08-13
 */
@Component
public class ElasticsearchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtils.class);

    private static final Integer INDEX_NUMBER_OF_SHARDS = 3;

    private static final Integer INDEX_NUMBER_OF_REPLICAS = 2;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static RestHighLevelClient client;

    @PostConstruct
    public void init() {
        client = this.restHighLevelClient;
    }

    /**
     * Creates an index using the Create Index API.
     * (It is recommended to manually create index and mapping.)
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    public static boolean createIndex(String index) throws IOException {
        LOGGER.info("Create index param: index={}", index);

        if (existsIndex(index)) {
            return false;
        }

        CreateIndexRequest request = new CreateIndexRequest(index);

        request.settings(Settings.builder()
                .put("index.number_of_shards", INDEX_NUMBER_OF_SHARDS)
                .put("index.number_of_replicas", INDEX_NUMBER_OF_REPLICAS)
        );

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

        boolean acknowledged = createIndexResponse.isAcknowledged();

        LOGGER.info("Create index successfully? {}", acknowledged);

        return acknowledged;
    }

    /**
     * Create or Updates the mappings on an index using the Put Mapping API.（XContentBuilder object）
     * (It is recommended to manually create index and mapping.)
     *
     * @param index    the index
     * @param proNames the pro names
     * @return b
     * @throws IOException the IOException
     */
    public static boolean createMapping(String index, Map<String, Map<String, String>> proNames) throws IOException {
        LOGGER.info("Create Mapping param: index={}, proNames={}", index, JacksonUtils.toJson(proNames));

        if (!existsIndex(index)) {
            return false;
        }

        PutMappingRequest request = new PutMappingRequest(index);

        XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("dynamic", "strict").startObject("properties");

        if (!proNames.isEmpty()) {
            for (Map.Entry<String, Map<String, String>> proName : proNames.entrySet()) {
                if (null != proName.getKey()) {
                    builder.startObject(proName.getKey());

                    if (!proName.getValue().isEmpty()) {

                        for (Map.Entry<String, String> filed : proName.getValue().entrySet()) {
                            String pro = filed.getKey();
                            String value = filed.getValue();

                            if (null != pro && null != value) {
                                builder.field(pro, value);
                            }
                        }
                    }

                    builder.endObject();
                }
            }
        }
        builder.endObject().endObject();
        request.source(builder);

        AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);

        boolean acknowledged = putMappingResponse.isAcknowledged();

        LOGGER.info("Create mapping successfully? {}", acknowledged);

        return acknowledged;
    }

    /**
     * Updates the mappings on an index using the Put Mapping API (Outer JSON format).
     *
     * @param index       the index
     * @param mappingJson the mapping json
     * @return b
     * @throws IOException the IOException
     */
    public static boolean createMapping(String index, String mappingJson) throws IOException {
        LOGGER.info("Create Mapping param: index={}, mappingJson={}", index, JacksonUtils.toJson(mappingJson));

        if (!existsIndex(index)) {
            return false;
        }

        if (StringUtils.isEmpty(mappingJson)) {
            LOGGER.error("The mapping json is not-exist or json content is empty!");

            return false;
        }

        PutMappingRequest request = new PutMappingRequest(index);

        ///String json = "{\"properties\": {\"message\": {\"type\": \"text\"}}}";

        ///request.source(json, XContentType.JSON);

        request.source(mappingJson, XContentType.JSON);

        AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);

        boolean acknowledged = putMappingResponse.isAcknowledged();

        LOGGER.info("Create mapping json successfully? {}", acknowledged);

        return acknowledged;
    }

    /**
     * Updates the mappings on an index using the Put Mapping API (Internal JSON format).
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    public static boolean createMapping(String index) throws IOException {

        String fileName = "mappings/" + index + "-mapping.json";

        String mappingJson = new ClassPathResourceReader(fileName).getContent();

        return createMapping(index, mappingJson);

    }

    /**
     * Deletes an index using the Delete Index API.
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    public static boolean deleteIndex(String index) throws IOException {
        LOGGER.info("Delete index param: index={}", index);

        if (!existsIndex(index)) {
            return false;
        }

        DeleteIndexRequest request = new DeleteIndexRequest(index);

        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();

        LOGGER.info("Delete index successfully? {}", acknowledged);

        return acknowledged;
    }

    /**
     * Checks if the index (indices) exists or not.
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    public static boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        if (exists) {
            LOGGER.info("The index [{}] is exist!", index);
        } else {
            LOGGER.info("The index [{}] is not exist!", index);
        }

        return exists;
    }

    /**
     * Index a document using the Index API.
     *
     * @param jsonObject the JSON Object String
     * @param index      the index
     * @param id         The document id
     * @return id
     * @throws IOException the IOException
     */
    public static String createDocument(JSONObject jsonObject, String index, String id) throws IOException {
        LOGGER.info("Batch create document param: json={}, index={}, id={}", jsonObject, index, id);

        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(jsonObject, XContentType.JSON);

        // can be create or update (default) change only create
        request.opType(DocWriteRequest.OpType.CREATE);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        LOGGER.info("Create document response status: {}, id: {}", response.status().getStatus(), response.getId());

        return response.getId();
    }

    /**
     * Index a document using the Index API and Auto create documentId.
     *
     * @param jsonObject the JSON Object String
     * @param index      The index
     * @return id
     * @throws IOException IOException
     */
    public static String createDocument(JSONObject jsonObject, String index) throws IOException {
        return createDocument(jsonObject, index, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * Updates a document using the Update API.
     *
     * @param jsonObject The JSON Object Data String
     * @param index      The index
     * @param id         id
     * @throws IOException IOException
     */
    public static void updateDocumentById(JSONObject jsonObject, String index, String id) throws IOException {
        LOGGER.info("Batch update document by id param: json={}, index={}, id={}", jsonObject, index, id);

        UpdateRequest request = new UpdateRequest(index, id);

        request.doc(jsonObject, XContentType.JSON);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        LOGGER.info("Update document response status: {}, id: {}", response.status().getStatus(), response.getId());
    }

    /**
     * Executes a bulk request using the Bulk API.
     *
     * @param batchJsonObject the batch JsonObject
     * @param index           the index
     * @param ids             the ids
     * @throws IOException IOException
     */
    public static void batchCreateDocument(List<JSONObject> batchJsonObject, String index, String... ids) throws IOException {
        LOGGER.info("Executes batch create document param: list={}, ids={}", batchJsonObject, ids);

        BulkRequest request = new BulkRequest();

        if (batchJsonObject == null || batchJsonObject.isEmpty()) {
            throw new CommonException("List is empty.");
        }

        //// batchJsonObject.forEach(e -> request.add(new IndexRequest(index).id(ids[0]).source(e, XContentType.JSON)));

        ForEachUtils.forEach(0, batchJsonObject, (_index, _item) -> {
            request.add(new IndexRequest(index).id(ids[_index]).source(_item, XContentType.JSON));
        });

        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);

        LOGGER.info("Executes batch create document response status: {}, name: {}", responses.status().getStatus(), responses.status().name());

    }


}
