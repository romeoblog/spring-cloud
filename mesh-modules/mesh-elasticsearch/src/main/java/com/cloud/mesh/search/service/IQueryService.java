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
package com.cloud.mesh.search.service;

import com.cloud.mesh.model.search.QueryRequestVO;
import com.cloud.mesh.search.utils.ElasticsearchPage;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A Query that matches documents matching interface service
 *
 * @author Benji
 * @date 2019-08-17
 */
public interface IQueryService {

    /**
     * Retrieves a document by id using the Get API.
     *
     * @param index  the index
     * @param id     the id
     * @param fields the list of include field
     * @return m
     * @throws IOException the IOException
     */
    Map<String, Object> getDocumentById(String index, String id, String fields) throws IOException;

    /**
     * A Query that matches documents matching using the matches List API.
     *
     * @param data the data params
     * @return l
     * @throws IOException the IOException
     */
    List<Map<String, Object>> queryForList(QueryRequestVO data) throws IOException;

    /**
     * A Query that matches documents matching using the matches List API.
     *
     * @param index          the index
     * @param termName       the term name
     * @param startTime      the start time
     * @param endTime        the end time
     * @param size           the page size
     * @param fields         the list of include field
     * @param sortField      The name of the field with sort desc
     * @param matchPhrase    checks if matchPhrase
     * @param highlightField the highlight of the field
     * @param matchStr       the matchStr of the field
     * @return l
     * @throws IOException the IOException
     */
    @Deprecated
    List<Map<String, Object>> queryForList(String index, String termName, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException;


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
    List<Map<String, Object>> queryForList(String index, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) throws IOException;

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
    @Deprecated
    ElasticsearchPage queryForPage(String index, int currentPage, int pageSize, String termName, long startTime, long endTime, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) throws IOException;

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
    ElasticsearchPage queryForPage(String index, int currentPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) throws IOException;

    /**
     * A Query that matches documents matching using the matches Page API.
     *
     * @param data the data params
     * @return e
     * @throws IOException the IOException
     */
    ElasticsearchPage queryForPage(QueryRequestVO data) throws IOException;

}

