package com.cloud.mesh.java8;

import com.cloud.mesh.common.utils.StringUtils;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Map;

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
	}
}