package com.cloud.example.search.utils;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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


}
