package org.combo.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final OkHttpClient client = new OkHttpClient();

    public static final MediaType URL_ENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static ResponseProxy doGet(String url) {
        return doGet(url, null, null);
    }

    public static ResponseProxy doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    public static ResponseProxy doGet(String url, Map<String, String> params, Map<String, String> headers) {
        String paramsStr = buildParams(params);
        String executeUrl;
        if(StringUtils.isNotEmpty(paramsStr)) {
            executeUrl = url + "?" + paramsStr;
        } else {
            executeUrl = url;
        }
        Request.Builder builder = new Request.Builder();
        builder.url(executeUrl);
        buildHeaders(headers, builder);
        Request request = builder.build();
        try {
            return new ResponseProxy(client.newCall(request).execute());
        } catch (IOException e) {
            logger.error("execute request to " + url + " meet an IOException", e);
        }
        //网络通信可能出现异常，返回请求超时（408）的一个响应
        return new ResponseProxy(new Response.Builder().code(408).build());
    }

    public static ResponseProxy doPost(String url) {
        return doPost(url, null, null);
    }

    public static ResponseProxy doPost(String url, Map<String, String> params) {
        return doPost(url, params, null);
    }

    public static ResponseProxy doPost(String url, Map<String, String> params, Map<String, String> headers) {
        String paramsStr = buildParams(params);
        Request.Builder builder = new Request.Builder().url(url);
        if(StringUtils.isNotEmpty(paramsStr)) {
            RequestBody requestBody = RequestBody.create(URL_ENCODED, paramsStr);
            builder.post(requestBody);
        }

        buildHeaders(headers, builder);
        Request request = builder.build();
        try {
            return new ResponseProxy(client.newCall(request).execute());
        } catch (IOException e) {
            logger.error("execute request to " + url + " meet an IOException", e);
        }
        //网络通信可能出现异常，返回请求超时（408）的一个响应
        return new ResponseProxy(new Response.Builder().code(408).build());
    }

    private static void buildHeaders(Map<String, String> headers, Request.Builder builder) {
        if(MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String headerName = entry.getKey();
                String headerValue = entry.getValue();
                builder.addHeader(headerName, headerValue);
            }
        }
    }

    private static String buildParams(Map<String, String> params) {
        if(MapUtils.isNotEmpty(params)) {
            StringBuffer buffer = new StringBuffer();
            List<String> paramToJoin = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String paramName = entry.getKey();
                String paramValue = entry.getValue();
                paramToJoin.add(paramName + "=" + paramValue);
            }
            return buffer.append(StringUtils.join(paramToJoin, "&")).toString();
        }
        return null;
    }

    static class ResponseProxy {
        private final Response response;

        public ResponseProxy(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }

        public boolean isSuccessful() {
            return response.isSuccessful();
        }

        public String string() {
            try {
                return response.body().string();
            } catch (IOException e) {
                logger.error("extract response body meet an exception", e);
            }
            return null;
        }
    }
}
