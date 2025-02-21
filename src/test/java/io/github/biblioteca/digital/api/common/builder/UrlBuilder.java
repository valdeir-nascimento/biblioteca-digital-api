package io.github.biblioteca.digital.api.common.builder;

import java.util.Map;
import java.util.StringJoiner;

public class UrlBuilder {

    private final StringBuilder urlBuilder;
    private final StringJoiner pathJoiner;
    private boolean hasQueryParameters = false;

    private UrlBuilder(String baseUrl) {
        this.urlBuilder = new StringBuilder(baseUrl);
        this.pathJoiner = new StringJoiner("/");
    }

    public static UrlBuilder builder(String baseUrl) {
        return new UrlBuilder(baseUrl);
    }

    public UrlBuilder path(String path) {
        this.pathJoiner.add(path);
        return this;
    }

    public UrlBuilder pathVariable(Object pathVariable) {
        this.pathJoiner.add(pathVariable.toString());
        return this;
    }

    public UrlBuilder queryParam(String key, Object value) {
        appendQueryDelimiter();
        this.urlBuilder.append(key).append("=").append(value.toString());
        return this;
    }

    public UrlBuilder queryParams(Map<String, Object> params) {
        params.forEach(this::queryParam);
        return this;
    }

    public String build() {
        if (pathJoiner.length() > 0) {
            this.urlBuilder.append("/").append(this.pathJoiner.toString());
        }
        return this.urlBuilder.toString();
    }

    private void appendQueryDelimiter() {
        if (!hasQueryParameters) {
            this.urlBuilder.append("?");
            hasQueryParameters = true;
        } else {
            this.urlBuilder.append("&");
        }
    }
}
