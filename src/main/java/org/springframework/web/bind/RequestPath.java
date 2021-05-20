package org.springframework.web.bind;

import java.util.Objects;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/20 18:49
 * Copyright 2021 by WiteMedia
 */
public class RequestPath {

    private String url;

    private String type;

    public RequestPath(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestPath)) return false;
        RequestPath that = (RequestPath) o;
        return Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getType());
    }
}
