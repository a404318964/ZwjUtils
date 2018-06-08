package com.httpclient;

import com.github.a404318964.zwjutils.GeneratorUtil;
import com.httpclient.common.HttpConfig;
import com.httpclient.common.HttpHeader;
import com.httpclient.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by zwj on 2017/4/7.
 */
@Slf4j
public class RequestBuilder {
    public static final String APPLICATION_JSON = "application/json";

    // 访问编码
    private String id;

    /**
     * 最大重连次数
     */
    public static final int MAX_RECONNECTION_COUNT = 2;
    public static final int METHOD_GET = 2000;
    public static final int METHOD_POST = 2001;

    private String url;
    /**
     * 请求的方式
     */
    private int requestMethod;

    private String bodyContent; // 以json形式传递的参数
    private int timeOut = 10 * 10000;   // 连接超时时间，单位毫秒
    private Header[] headers;
    private HttpHeader httpHeader;


    /**
     * 存放参数
     */
    private Map<String, Object> paramMap = new HashMap<>();

    public RequestBuilder() {
        id = GeneratorUtil.generatorUUID();
    }

    public RequestBuilder(String url) {
        this(url, METHOD_POST);
    }

    public RequestBuilder(String url, int requestMethod) {
        this();
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public RequestBuilder setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public RequestBuilder setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        addContentType(APPLICATION_JSON);
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public RequestBuilder setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public Header[] getHeaders() {
        if (headers == null) {
            if (httpHeader != null) {
                headers = httpHeader.build();
            }
        }
        return headers;
    }

    public RequestBuilder setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder addHeader(String key, String value) {
        if (httpHeader == null) {
            httpHeader = HttpHeader.custom();
        }

        if (StringUtils.isNotBlank(value)) {
            if ("Content-Type".equalsIgnoreCase(key)) {
                addContentType(value);
            } else {
                httpHeader.other(key, value);
            }
        }

        return this;
    }

    public RequestBuilder addContentType(String value) {
        if (httpHeader == null) {
            httpHeader = HttpHeader.custom();
        }
        if (StringUtils.isNotBlank(value)) {
            httpHeader.contentType(value);
        }
        return this;
    }

    public RequestBuilder addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }

        if (StringUtils.isNotBlank(value))
            paramMap.put(key, value);
        return this;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResponseBean request(boolean addDefaultHeader) {
//        if (addDefaultHeader) {
//            addHeader(Constant.DEVICE_REQUEST_PARAM, Constant.THIRD_APPLICATION_DEVICE_TAG)
//                    .addHeader(Constant.REQUEST_PARAM_TOKEN, BaseContextHandler.getToken())
//                    .addHeader(Constant.REQUEST_PARAM_EID, BaseContextHandler.getEid())
//                    .addHeader(Constant.REQUEST_PARAM_COMMUNITY_ID, BaseContextHandler.getCommunityId())
//                    .addHeader(Constant.REQUEST_PARAM_APPID, BaseContextHandler.getAppId());
//        }
        return requestHttp(this);
    }

    public ResponseBean request() {
        return request(false);
    }

    private static ResponseBean requestHttp(RequestBuilder requestBuilder) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(requestBuilder.getId());

        log.info("request url ---> " + requestBuilder.getUrl());

        if (requestBuilder == null) {
            responseBean.setCode(ResponseBean.ERROR).setErrorMsg("requestBuilder is null");
        } else {
            HttpConfig config = HttpConfig.custom()
                    .url(requestBuilder.getUrl()) //设置请求的url
                    .encoding("utf-8")//设置请求和返回编码，默认就是Charset.defaultCharset()
                    ;

            if (StringUtils.isBlank(requestBuilder.getBodyContent())) {

                // 打印请求参数
                if (requestBuilder.getParamMap() != null) {
                    Set<String> keySet = requestBuilder.getParamMap().keySet();
                    Iterator<String> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        log.info("net param key --> " + key + ";  value --> " + requestBuilder.getParamMap().get(key));
                    }
                }

                if (requestBuilder.getRequestMethod() == RequestBuilder.METHOD_POST) {
                    config.map(requestBuilder.getParamMap());
                }
            } else {
                log.info("request body --> " + requestBuilder.getBodyContent());
                // json形式提交
                requestBuilder.addContentType(APPLICATION_JSON);
                config.json(requestBuilder.getBodyContent());
                requestBuilder.setRequestMethod(RequestBuilder.METHOD_POST);
            }

            if (requestBuilder.getHeaders() != null) {
                config.headers(requestBuilder.getHeaders());
            }

            try {
                String result = null;
                if (requestBuilder.getRequestMethod() == RequestBuilder.METHOD_GET) {

                    if (requestBuilder.getParamMap() != null) {
                        StringBuilder sbUrl = new StringBuilder();
                        sbUrl.append(requestBuilder.getUrl());

                        int count = 0;
                        Set<String> keySet = requestBuilder.getParamMap().keySet();
                        Iterator<String> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();

                            if (count == 0) {
                                sbUrl.append("?");
                            } else {
                                sbUrl.append("&");
                            }
                            sbUrl.append(key).append("=").append(requestBuilder.getParamMap().get(key));

                            count++;
                        }

                        log.info("get url ---> " + sbUrl.toString());
                        config.url(sbUrl.toString());
                        result = HttpClientUtil.get(config);
                    } else {
                        result = HttpClientUtil.get(config);
                    }
                } else if (requestBuilder.getRequestMethod() == RequestBuilder.METHOD_POST) {
                    result = HttpClientUtil.post(config);   //post请求
                }

                responseBean.setCode(ResponseBean.SUCCESS).setResult(result.trim());
                log.info("request result ---> " + result.trim());
            } catch (HttpProcessException e) {
                responseBean.setCode(ResponseBean.ERROR).setErrorMsg(e.getMessage());
                e.printStackTrace();
            }
        }
        return responseBean;
    }
}
