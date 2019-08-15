package com.cloud.example.search.utils;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
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
import java.util.Map;

/**
 * The type elasticsearch utils
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
     * 创建索引（建议手工建立索引和映射）
     *
     * @param index 索引名
     * @return 创建是否成功
     * @throws IOException 异常信息
     */
    public static boolean createIndex(String index) throws IOException {

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
     * 创建索引映射（XContentBuilder object）（建议手工建立索引和映射）
     *
     * @param index    索引名
     * @param proNames 字段参数集
     * @return 创建是否成功
     * @throws Exception
     */
    public static boolean createMapping(String index, Map<String, Map<String, String>> proNames) throws Exception {

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
     * 删除索引
     *
     * @param index 索引名
     * @return 执行是否成功
     * @throws IOException 异常信息
     */
    public static boolean deleteIndex(String index) throws IOException {

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
     * 判断索引是否存在
     *
     * @param index 索引名
     * @return 索引是否存在
     * @throws IOException 异常信息
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
     * The type Create document in ES
     *
     * @param jsonObject The JSON Object Data String
     * @param index      The index
     * @param id         The Current Data id
     * @return id
     * @throws IOException IOException
     */
    public static String createDocument(JSONObject jsonObject, String index, String id) throws IOException {

        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(jsonObject, XContentType.JSON);

        // can be create or update (default) change only create
        request.opType(DocWriteRequest.OpType.CREATE);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        return response.getId();
    }


}
