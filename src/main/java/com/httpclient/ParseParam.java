package com.httpclient;

/**
 * Created by zwj on 2017/7/10.
 */
public class ParseParam<T> {
//    private String data;
    private Class<T> cls;

    // 默认code 1为成功
    private int successCode = 1;
    // 默认返回code的标签
    private String codeTag = "code";

//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }

    public ParseParam() {
        super();
    }

    public ParseParam(Class<T> cls) {
        this.cls = cls;
    }

    public Class<T> getCls() {
        return cls;
    }

    public ParseParam<T> setCls(Class<T> cls) {
        this.cls = cls;
        return this;
    }

    public int getSuccessCode() {
        return successCode;
    }

    public ParseParam<T> setSuccessCode(int successCode) {
        this.successCode = successCode;
        return this;
    }

    public String getCodeTag() {
        return codeTag;
    }

    public ParseParam<T> setCodeTag(String codeTag) {
        this.codeTag = codeTag;
        return this;
    }
}
