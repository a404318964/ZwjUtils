package com.httpclient;

import cc.iqcloud.cloud.command.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zwj on 2017/4/7.
 */
public class ResponseBean {
    public static int ERROR = 0;
    public static int SUCCESS = 1;

    private String url;
    private int code;
    private String result;
    private String errorMsg;

    public int getCode() {
        return code;
    }

    public ResponseBean setCode(int code) {
        this.code = code;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ResponseBean setResult(String result) {
        this.result = result;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ResponseBean setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }

    public <T> T getObject(ParseParam<T> param) {
        if(isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.optInt(param.getCodeTag());
                if (code == param.getSuccessCode()) {
                    String data = jsonObject.optString("data");
                    if (!StringUtils.isBlank(data)) {
                        return JsonUtil.getObject(data, param.getCls());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public <T> T getObject(Class<T> cls) {
        return getObject(new ParseParam<T>().setCls(cls));
    }

    public <T> List<T> getObjects(ParseParam<T> param) {
        if(isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.optInt(param.getCodeTag());
                if (code == param.getSuccessCode()) {
                    String data = jsonObject.optString("data");
                    if (!StringUtils.isBlank(data)) {
                        return JsonUtil.getObjects(data, param.getCls());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public <T> List<T> getObjects(Class<T> cls) {
        return getObjects(new ParseParam<T>().setCls(cls));
    }

    public String getUrl() {
        return url;
    }

    public ResponseBean setUrl(String url) {
        this.url = url;
        return this;
    }
}
