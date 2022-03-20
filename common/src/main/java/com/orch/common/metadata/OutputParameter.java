package com.orch.common.metadata;

import java.util.List;

public class OutputParameter {
    private String name;
    private Object value;
    private String type;
    private List<List<String>> retain;
    private List<List<String>> exclude;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<String>> getRetain() {
        return retain;
    }

    public void setRetain(List<List<String>> retain) {
        this.retain = retain;
    }

    public List<List<String>> getExclude() {
        return exclude;
    }

    public void setExclude(List<List<String>> exclude) {
        this.exclude = exclude;
    }

}
