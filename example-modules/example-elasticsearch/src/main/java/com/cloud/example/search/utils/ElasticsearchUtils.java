package com.cloud.example.search.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
     * 创建索引（不带映射，交给es识别字段的类型，并创建相应的映射）
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

        LOGGER.info("Create index success? {}", acknowledged);

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

        LOGGER.info("Delete index success? {}", acknowledged);

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
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        if (exists) {
            LOGGER.info("Index [{}] is exist!", index);
        } else {
            LOGGER.info("Index [{}] is not exist!", index);
        }

        return exists;
    }


}
