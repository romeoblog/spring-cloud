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
package com.cloud.mesh.java8;

import com.cloud.mesh.common.utils.StringUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UrlUtil {

	public static class UrlEntity {
		/**
		 * 基础url
		 */
		public String baseUrl;
		/**
		 * url参数
		 */
		public Map<String, String> params;
	}

	/**
	 * 解析url
	 *
	 * @param url
	 * @return
	 */
	public static Map<String, String> parse(String url, String key) {
		if (url == null || url.indexOf("?") == -1) {
			return null;
		}
		url = url.trim().substring(url.indexOf("?") + 1).replace("?","&");
		//有参数
		String[] params = url.split("&");
		Map<String, String> split = new HashMap<>();
		for (String param : params) {
			String[] keyValue = param.split("=");
			split.put(keyValue[0], keyValue[1]);
		}

		return split;
	}

    public static String getParam(String url, String key) {

        if (url == null || url.indexOf("?") == -1) {
            return null;
        }
        String params = url.substring(url.indexOf("?") + 1);
        if (StringUtils.isNotBlank(params)) {
            params = params.replace("?","&").substring(0, params.indexOf("&v="));
        }

        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return split.get(key);
    }

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(entity.baseUrl + "\n" + entity.params);
		String s = "https://mall-h5.dg-mall.com/mall-h5/usercenter/index?source=jxtt&v=LRLJRaTweGgIuEExqPNrM%2BXvNuGQubBCN0ksujkOcUphkFAjOxESjph6N8WMfSpCdU7aEoASZ351JdzP7SrdOdwaeLMgs%2Bn9fE3jfzaxGYhL%2BnhOSwTj3kvnoHfP%2BPrNoRj0%2FJJAKFOg%2FSnN7LvvnSZAB4TMThYCqjEFwRbE8uY%3D";
		String key = "https://mall-h5.dg-mall.com/mall-h5/h5-login/toLogin?referUrl=/cart/getBuyCart?source=jxtt&v=LV0otHRyj6HQlTSR7W8rBO6%2FLfwSLd2Dp96QwWa65wOJyxbEEGqoUzXHhd8%2FhgNAvsM86xr3xvfy0jHxzM%2FXb3Mj12o0UZv688S3zMi%2FFNIeLfn5yRQobK7JaNcNFy89%2B4N7N%2Fe8WdBj5EvLWld21RnlbCgL2eK1PAii5J9l%2BKk%3D";

		System.out.println(parse(s, null));
		System.out.println(parse(key, null));
//        System.out.println(getParam(key, "ss"));

        List<String> list = Lists.newArrayList();
        list.add("2");

        list = list.stream().filter(ss -> !Objects.equals(ss, "2")).collect(Collectors.toList());

        System.out.println(list);

	}
}