package com.ecms.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private final static int SOCK_TIMEOUT = 10;
    private final static int CONN_TIMEOUT = 15;
    private final static RequestConfig config = RequestConfig.custom().setSocketTimeout(SOCK_TIMEOUT * 1000)
            .setConnectTimeout(CONN_TIMEOUT * 1000).build();
    private static final int HTTP_STATUS_CODE_SUCCESS = 200;
    private static final String CHARSET_UTF8 = "UTF-8";

    public static HttpResponse get(String url) {
        HttpGet method = new HttpGet(url);
        return executeMethod(method);
    }

    public static HttpResponse get(String url, Map<String, ?> paramsMap) {
        url = assembleUrl(url, paramsMap);
        HttpGet method = new HttpGet(url);
        return executeMethod(method);
    }

    /**
     * 组装url, 在原有url后面通过?${key1}=${value1}&${key2}=${value2}...格式来拼接参数
     * @param url           原有url
     * @param paramsMap     参数key-value集合
     * @return
     */
    private static String assembleUrl(String url, Map<String, ?> paramsMap) {
        if (paramsMap != null) {
            Set<String> keySet = paramsMap.keySet();
            StringBuilder sb = new StringBuilder(keySet.size() * 8);
            sb.append("?");
            for (String key : keySet) {
                try {
                    String value = URLEncoder.encode(String.valueOf(paramsMap.get(key)), CHARSET_UTF8);
                    sb.append(key).append("=").append(value).append("&");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            String params = sb.toString();
            url += params.substring(0, params.length() - 1);
        }
        return url;
    }

    public static HttpResponse post(String url, Map<String, ?> paramsMap) {
        HttpPost method = new HttpPost(url);
        if (paramsMap != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>(paramsMap.size());
            Set<String> keySet = paramsMap.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key).toString()));
            }
            try {
                method.setEntity(new UrlEncodedFormEntity(nvps, CHARSET_UTF8));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return executeMethod(method);
    }

    /**
     * post请求连接
     * @param url 请求服务端url
     * @param body 请求参数
     * @param requestHeader 请求头
     * @return 返回服务端返回结果
     */
    public static HttpResponse post(String url, String body, String requestHeader) {
        HttpPost method = new HttpPost(url);
        method.setEntity(new StringEntity(body, ContentType.create(requestHeader, CHARSET_UTF8)));
        return executeMethod(method);
    }

    public static HttpResponse postXml(String url, String body) {
        HttpPost method = new HttpPost(url);
        method.setEntity(new StringEntity(body, ContentType.create("text/xml", CHARSET_UTF8)));
        return executeMethod(method);
    }

    public static HttpResponse postJson(String url, String body) {
        HttpPost method = new HttpPost(url);
        method.setEntity(new StringEntity(body, ContentType.create("application/json", CHARSET_UTF8)));
        return executeMethod(method);
    }

    private static final HttpResponse executeMethod(HttpRequestBase method) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setSuccess(false);
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        method.setConfig(config);
        try {
            org.apache.http.HttpResponse response = client.execute(method);
            if (HTTP_STATUS_CODE_SUCCESS == response.getStatusLine().getStatusCode()) {
                httpResponse.setSuccess(true);
            }
            result = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
            httpResponse.setResult(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            httpResponse.setResult(e.getMessage());
        } finally {
            method.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return httpResponse;
    }

    public static class HttpResponse {
        private boolean success;
        private String result;

        public boolean isSuccess() {
            return success;
        }

        void setSuccess(boolean success) {
            this.success = success;
        }

        public String getResult() {
            return result;
        }

        void setResult(String result) {
            this.result = result;
        }
    }

    public static void main(String[] args){
        HttpUtils.get("http://localhost:8080/tt/tt/test");

    }

}