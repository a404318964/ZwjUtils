package com.github.a404318964.zwjutils.qrcode;

public class QRCodeParam {

    private String content;     // 内容
    private String filePath;    // 源图片路径
    private String logoUrl;     // logo（网络路径）

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
