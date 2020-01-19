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
package com.cloud.mesh.search.async.service.impl;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.async.service.ElasticsearchService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Elasticsearch Client APIs.
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void insertById(String index, String id, Map<String, Object> dataMap) {
        LOGGER.info("Create a document param: json={}, index={}, id={}", JacksonUtils.toJson(dataMap), index, id);

        IndexRequest indexRequest = new IndexRequest(index).id(id).source(dataMap);

        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            LOGGER.info("Create document response status: {}, id: {}", response.status().getStatus(), response.getId());
        } catch (Exception ex) {
            LOGGER.error("Create document response Error: index={}, id={}, data={}", index, id, JacksonUtils.toJson(dataMap), ex);
        }

    }

    @Override
    public void batchInsertById(String index, Map<String, Map<String, Object>> idDataMap) {
        BulkRequest request = new BulkRequest(index);

        idDataMap.forEach((id, dataMap) -> request.add(new IndexRequest(index).id(id).source(dataMap)));
        try {
            BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            LOGGER.info("Executes batch create document response status: {}, name: {}", responses.status().getStatus(), responses.status().name());
        } catch (Exception ex) {
            LOGGER.info("Executes batch create document response Error: index={}, data={}", index, JacksonUtils.toJson(idDataMap), ex);
        }
    }

    @Override
    public void update(String index, String id, Map<String, Object> dataMap) {
        this.insertById(index, id, dataMap);
    }

    @Override
    public void deleteById(String index, String id) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);

        try {
            AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

            boolean acknowledged = response.isAcknowledged();

            LOGGER.info("Delete index successfully? {}", acknowledged);

        } catch (Exception ex) {
            LOGGER.info("Delete index Document Error: index={}, id={}", index, id, ex);
        }

    }
}
