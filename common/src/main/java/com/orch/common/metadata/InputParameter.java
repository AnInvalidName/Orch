package com.orch.common.metadata;

import java.util.List;

public class InputParameter {
    private String name;
    private List<String> fields;
    private Object value;
    private String type;
    private ParamReference valueSource;
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ParamReference getValueSource() {
        return valueSource;
    }

    public void setValueSource(ParamReference valueSource) {
        this.valueSource = valueSource;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
