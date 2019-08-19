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
package com.cloud.mesh.search.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.core.exception.ElasticsearchException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A wrapper for the ElasticsearchUtils that provides methods for accessing the Indices API.
 *
 * @author Benji
 * @date 2019-08-13
 */
@Component
public class ElasticsearchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtils.class);

    private static Integer shards;

    private static Integer replicas;

    private static final String SEPARATOR_COMMA = ",";

    private static final String SEPARATOR_EQUAL = "=";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static RestHighLevelClient client;

    @PostConstruct
    public void init() {
        client = this.restHighLevelClient;
    }

    @Value("${elasticsearch.index.shards}")
    public void setShards(Integer shards) {
        ElasticsearchUtils.shards = shards;
    }

    @Value("${elasticsearch.index.replicas}")
    public void setReplicas(Integer replicas) {
        ElasticsearchUtils.replicas = replicas;
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

        existsIndex(index);

        CreateIndexRequest request = new CreateIndexRequest(index);

        request.settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
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

        existsIndex(index);

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
     * Create or Updates the mappings on an index using the Put Mapping API (Outer JSON format).
     *
     * @param index       the index
     * @param mappingJson the mapping json
     * @return b
     * @throws IOException the IOException
     */
    public static boolean createMapping(String index, JSONObject mappingJson) throws IOException {
        LOGGER.info("Create Mapping param: index={}, mappingJson={}", index, JacksonUtils.toJson(mappingJson));

        existsIndex(index);

        if (StringUtils.isEmpty(mappingJson.toJSONString())) {
            throw new ElasticsearchException("The mapping json is not-exist or json content is empty!");
        }

        PutMappingRequest request = new PutMappingRequest(index);

        ///String json = "{\"properties\": {\"message\": {\"type\": \"text\"}}}";

        ///request.source(json, XContentType.JSON);

        request.source(mappingJson.toJSONString(), XContentType.JSON);

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

        existsIndex(index);

        String fileName = "mappings/" + index + "-mapping.json";

        String mappingJson = new ClassPathResourceReader(fileName).getContent();

        return createMapping(index, JSONObject.parseObject(mappingJson));

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
            throw new ElasticsearchException("The Delete Index[{" + index + "}] is not exists");
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
            LOGGER.info("The index [{}] already exists.", index);
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

        if (!existsIndex(index)) {
            throw new ElasticsearchException("Create a document index[{" + index + "}] is not exists");
        }

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
     * @throws IOException the IOException
     */
    public static String createDocument(JSONObject jsonObject, String index) throws IOException {
        return createDocument(jsonObject, index, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * Updates a document using the Update API.
     *
     * @param jsonObject The JSON Object Data String
     * @param index      the index
     * @param id         the id
     * @throws IOException the IOException
     */
    public static void updateDocumentById(JSONObject jsonObject, String index, String id) throws IOException {
        LOGGER.info("Update document by id param: json={}, index={}, id={}", jsonObject, index, id);

        if (!existsIndex(index)) {
            throw new ElasticsearchException("Update a document index[{" + index + "}] is not exists");
        }

        UpdateRequest request = new UpdateRequest(index, id);

        request.doc(jsonObject, XContentType.JSON);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        LOGGER.info("Update document response status: {}, id: {}", response.status().getStatus(), response.getId());
    }

    /**
     * Deletes a document by id using the Delete API.
     *
     * @param index the index
     * @param id    the id
     * @throws IOException the IOException
     */
    public static void deleteDocumentById(String index, String id) throws IOException {
        LOGGER.info("Delete document by id param: index={}, id={}", index, id);

        if (!existsIndex(index)) {
            throw new ElasticsearchException("Deletes a document index[{" + index + "}] is not exists");
        }

        DeleteRequest request = new DeleteRequest(index, id);

        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);

        LOGGER.info("Delete document response status: {}, id: {}", deleteResponse.status().getStatus(), deleteResponse.getId());

    }

    /**
     * Executes a bulk request using the Bulk API.
     *
     * @param batchJsonObject the batch JsonObject
     * @param index           the index
     * @param ids             the ids
     * @throws IOException the IOException
     */
    public static void batchCreateDocument(List<JSONObject> batchJsonObject, String index, String... ids) throws IOException {
        LOGGER.info("Executes batch create document param: list={}, ids={}", batchJsonObject, ids);

        if (!existsIndex(index)) {
            throw new ElasticsearchException("Executes batch as the Index[{" + index + "}] is not exists");
        }

        BulkRequest request = new BulkRequest();

        if (batchJsonObject == null || batchJsonObject.isEmpty()) {
            throw new ElasticsearchException("Executes a bulk with the list is empty.");
        }

        //// batchJsonObject.forEach(e -> request.add(new IndexRequest(index).id(ids[0]).source(e, XContentType.JSON)));

        ForEachUtils.forEach(batchJsonObject, (i, item) -> request.add(new IndexRequest(index).id(ids[i]).source(item, XContentType.JSON)));

        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);

        LOGGER.info("Executes batch create document response status: {}, name: {}", responses.status().getStatus(), responses.status().name());

    }

    /**
     * A Query that matches documents matching using the matches List API.
     *
     * @param index          the index
     * @param termName       the term name
     * @param startTime      the start time
     * @param endTime        the end time
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param matchPhrase    checks if matchPhrase
     * @param highlightField the highlight of the field
     * @param matchStr       the matchStr of the field
     * @return l
     * @throws IOException the IOException
     */
    public static List<Map<String, Object>> searchListDocument(String index, String termName, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException {
        return searchDocument(index, 0, 0, termName, startTime, endTime, size, fields, sortField, matchPhrase, highlightField, matchStr);
    }

    /**
     * A Query that matches documents matching using the matches List API (Custom the query builder).
     *
     * @param index          the index
     * @param query          the queryBuilder
     * @param size           the page size
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param highlightField the highlight of the field
     * @return l
     * @throws IOException the IOException
     */
    public static List<Map<String, Object>> searchListDocument(String index, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) throws IOException {
        return searchDocument(index, 0, 0, query, size, fields, sortField, highlightField);
    }

    /**
     * A Query that matches documents matching using the matches Page API.
     *
     * @param index          the index
     * @param currentPage    the current page
     * @param pageSize       the page size
     * @param termName       the term name
     * @param startTime      the start time
     * @param endTime        the end time
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param matchPhrase    checks if matchPhrase
     * @param highlightField the highlight of the field
     * @param matchStr       the matchStr of the field
     * @return e
     * @throws IOException the IOException
     */
    public static ElasticsearchPage searchPageDocument(String index, int currentPage, int pageSize, String termName, long startTime, long endTime, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException {
        return searchDocument(index, currentPage, pageSize, termName, startTime, endTime, 0, fields, sortField, matchPhrase, highlightField, matchStr);
    }

    /**
     * A Query that matches documents matching using the matches Page API (Custom the query builder).
     *
     * @param index          the index
     * @param currentPage    the current page
     * @param pageSize       the page size
     * @param query          the queryBuilder
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param highlightField the highlight of the field
     * @return e
     * @throws IOException the IOException
     */
    public static ElasticsearchPage searchPageDocument(String index, int currentPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) throws IOException {
        return searchDocument(index, currentPage, pageSize, query, 0, fields, sortField, highlightField);
    }


    /**
     * A Query that matches documents matching using the matches API.
     *
     * @param index          the index
     * @param currentPage    the current page
     * @param pageSize       the page size
     * @param termName       the term name
     * @param startTime      the start time
     * @param endTime        the end time
     * @param size           the document size
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param matchPhrase    checks if matchPhrase
     * @param highlightField the highlight of the field
     * @param matchStr       the matchStr of the field
     * @param <T>            l or e
     * @return l or e
     * @throws IOException the IOException
     */
    private static <T> T searchDocument(String index, int currentPage, int pageSize, String termName, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException {
        BoolQueryBuilder boolQueryBuilder = handlerBoolQueryBuilder(termName, startTime, endTime, matchPhrase, matchStr);
        return searchDocument(index, currentPage, pageSize, boolQueryBuilder, size, fields, sortField, highlightField);
    }

    /**
     * A Query that matches documents matching using the matches API (Custom the query builder).
     *
     * @param index          the index
     * @param currentPage    the current page
     * @param pageSize       the page size
     * @param query          the queryBuilder
     * @param size           the document size
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param highlightField the highlight of the field
     * @param <T>            l or e
     * @return l or e
     * @throws IOException the IOException
     */
    private static <T> T searchDocument(String index, int currentPage, int pageSize, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) throws IOException {
        LOGGER.info("A Query that matches documents matching using the matches API Param: index={}, queryName={}, size={}, fields={}, sortField={}, highlightField={}",
                index, query.getName(), size, fields, sortField, highlightField);

        // The provided indices with the given search source.
        SearchRequest searchRequest = new SearchRequest(StringUtils.split(index, SEPARATOR_COMMA));

        // Set ignores unavailable indices、execute the search to prefer local shard. (default: to randomize across shards.)
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        searchRequest.preference("_local");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // A query that matches on all documents.
        /// sourceBuilder.query(QueryBuilders.matchAllQuery());

        /// BoolQueryBuilder boolQueryBuilder = handlerBoolQueryBuilder(termName, startTime, endTime, matchPhrase, matchStr);

        sourceBuilder.query(query);

        // Requesting highlightField
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            /// highlightBuilder.preTags("<span style='color:red' >");
            /// highlightBuilder.postTags("</span>");

            // Add the field highlighter to the highlight builder
            highlightBuilder.field(highlightField);
            sourceBuilder.highlighter(highlightBuilder);
        }

        // Include fields
        if (StringUtils.isNotEmpty(fields)) {
            sourceBuilder.fetchSource(fields.split(SEPARATOR_COMMA), null);
        }
        sourceBuilder.fetchSource(true);
        sourceBuilder.explain(true);
        if (StringUtils.isNotEmpty(sortField)) {
            sourceBuilder.sort(sortField, SortOrder.DESC);
        }

        boolean isPage = false;

        // default=10
        if (size != null && size > 0) {
            sourceBuilder.size(size);
        }

        // Set from and  size
        if (currentPage > 0 && pageSize > 0) {
            sourceBuilder.from(currentPage - 1).size(pageSize);
            isPage = true;
        }

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        long totalHits = searchResponse.getHits().getTotalHits().value;
        long length = searchResponse.getHits().getHits().length;
        LOGGER.info("The total number: {}, The hits of the search request number: {}", totalHits, length);


        List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

        if (isPage) {
            return (T) new ElasticsearchPage(currentPage, pageSize, (int) totalHits, sourceList);
        }

        return (T) sourceList;
    }

    /**
     * Handler SearchResponse
     *
     * @param searchResponse the searchResponse
     * @param highlightField the highlightField
     * @return list
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {

        if (searchResponse.status().getStatus() != RestStatus.OK.getStatus()) {
            LOGGER.error("The request has failed of the status={}. ", searchResponse.status().getStatus());
            return null;
        }

        List<Map<String, Object>> sourceList = Lists.newArrayList();
        StringBuffer stringBuffer = new StringBuffer();

        SearchHits hits = searchResponse.getHits();

        for (SearchHit hit : hits.getHits()) {
            hit.getSourceAsMap().put("id", hit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                Text[] text = hit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    hit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }

            sourceList.add(hit.getSourceAsMap());
        }

        LOGGER.info("A query that matches documents matching return result: {}", JacksonUtils.toJson(sourceList));

        return sourceList;
    }

    /**
     * A Query that matches documents matching boolean combinations of other queries.
     *
     * @param termName    the term name
     * @param startTime   the start time
     * @param endTime     the end time
     * @param matchPhrase checks if matchPhrase
     * @param matchStr    the matchStr of the field
     * @return
     */
    private static BoolQueryBuilder handlerBoolQueryBuilder(String termName, long startTime, long endTime, boolean matchPhrase, String matchStr) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotEmpty(termName) && startTime > 0 && endTime > 0) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery(termName)
                    .format("epoch_millis")
                    .from(startTime)
                    .to(endTime)
                    .includeLower(true)
                    .includeUpper(true));
        }

        // A query that matches document with type PHRASE or BOOLEAN
        if (StringUtils.isNotEmpty(matchStr)) {
            String[] matchStrArray = matchStr.split(SEPARATOR_COMMA);
            for (String s : matchStrArray) {
                String[] ss = s.split(SEPARATOR_EQUAL);
                if (ss.length > 1) {
                    if (matchPhrase == Boolean.TRUE) {
                        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(ss[0], ss[1]));
                    } else {
                        boolQueryBuilder.must(QueryBuilders.matchQuery(ss[0], ss[1]));
                    }
                }
            }
        }

        return boolQueryBuilder;

    }


}
