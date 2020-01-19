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

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * The Type Elasticsearch Page
 *
 * @author willlu.zheng
 * @date 2019-08-16
 */
@Data
public class ElasticsearchPage {

    /**
     * The type current page
     */
    private int currentPage;
    /**
     * The type page size
     */
    private int pageSize;

    /**
     * The typ es record count
     */
    private int recordCount;
    /**
     * The type es current page record list
     */
    private List<Map<String, Object>> recordList;

    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 页码列表的开始索引（包含）
     */
    private int beginPageIndex;
    /**
     * 页码列表的结束索引（包含）
     */
    private int endPageIndex;

    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属性的值
     *
     * @param currentPage
     * @param pageSize
     * @param recordCount
     * @param recordList
     */
    public ElasticsearchPage(int currentPage, int pageSize, int recordCount, List<Map<String, Object>> recordList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        this.recordList = recordList;

        // 计算总页码
        pageCount = (recordCount + pageSize - 1) / pageSize;

        // 计算 beginPageIndex 和 endPageIndex
        // >> 总页数不多于10页，则全部显示
        if (pageCount <= 10) {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        }
        // >> 总页数多于10页，则显示当前页附近的共10个页码
        else {
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = currentPage - 4;
            endPageIndex = currentPage + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > pageCount) {
                endPageIndex = pageCount;
                beginPageIndex = pageCount - 10 + 1;
            }
        }
    }
}