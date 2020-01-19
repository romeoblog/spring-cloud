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
package com.cloud.mesh.search.service.impl;

import com.cloud.mesh.model.search.QueryRequestVO;
import com.cloud.mesh.search.service.IQueryService;
import com.cloud.mesh.search.utils.ElasticsearchPage;
import com.cloud.mesh.search.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A Query that matches documents matching interface implements service
 *
 * @author willlu.zheng
 * @date 2019-08-17
 */
@Service
public class QueryServiceImpl implements IQueryService {

    @Override
    public Map<String, Object> getDocumentById(String index, String id, String fields) throws IOException {
        return ElasticsearchUtils.getDocumentById(index, id, fields);
    }

    @Override
    public List<Map<String, Object>> queryForList(QueryRequestVO data) throws IOException {
        return ElasticsearchUtils.searchListDocument(data.getIndex(), data.getTermName(), data.getStartTime(), data.getEndTime(), data.getSize(), data.getFields(), data.getSortField(), data.getMatchPhrase(), data.getHighlightField(), data.getMatchStr());
    }

    @Override
    public List<Map<String, Object>> queryForList(String index, String termName, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException {
        return ElasticsearchUtils.searchListDocument(index, termName, startTime, endTime, size, fields, sortField, matchPhrase, highlightField, matchStr);
    }

    @Override
    public List<Map<String, Object>> queryForList(String index, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) throws IOException {
        return ElasticsearchUtils.searchListDocument(index, query, size, fields, sortField, highlightField);
    }

    @Override
    public ElasticsearchPage queryForPage(String index, int currentPage, int pageSize, String termName, long startTime, long endTime, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException {
        return ElasticsearchUtils.searchPageDocument(index, currentPage, pageSize, termName, startTime, endTime, fields, sortField, matchPhrase, highlightField, matchStr);
    }

    @Override
    public ElasticsearchPage queryForPage(String index, int currentPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) throws IOException {
        return ElasticsearchUtils.searchPageDocument(index, currentPage, pageSize, query, fields, sortField, highlightField);
    }

    @Override
    public ElasticsearchPage queryForPage(QueryRequestVO data) throws IOException {
        return ElasticsearchUtils.searchPageDocument(data.getIndex(), data.getCurrentPage(), data.getPageSize(), data.getTermName(), data.getStartTime(), data.getEndTime(), data.getFields(), data.getSortField(), data.getMatchPhrase(), data.getHighlightField(), data.getMatchStr());
    }

}
