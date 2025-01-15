package com.example.gamereview;

public class Query {
    private String fields;
    private String sort;
    private int limit;

    public Query(String fields, String sort, int limit) {
        this.fields = fields;
        this.sort = sort;
        this.limit = limit;
    }

    // Getters and setters
    public String getFields() {
        return fields;
    }

    public String getSort() {
        return sort;
    }

    public int getLimit() {
        return limit;
    }
}
