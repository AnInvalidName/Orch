package com.orch.common.metadata;

import java.util.List;
import java.util.Map;

/**
 * 每个任务定义对应单一api接口
 */
public class TaskDef {
    private Integer id;
    private String name;
    private String description;
    private Integer version;
    private String type;

    /**
     * 是否为系统任务
     */
    private Boolean systemTask;
    /**
     * api接口是否为同步调用
     */
    private Boolean synchronous;
    /**
     * api接口输入参数名、参数类型、顺序，可为参数或参数字段预设值
     */
    //TODO 只有一个输入？一个输出？
    private Map<String, InputParameter> inputParams;
    /**
     * api接口输入参数名、参数类型
     */
    private Map<String, OutputParameter> outputParams;
    /**
     * 调用参数，如服务地址、服务名、重试次数等；
     * 可为参数预设值
     */
    private Map<String, InputParameter> executorParams;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSystemTask() {
        return systemTask;
    }

    public void setSystemTask(Boolean systemTask) {
        this.systemTask = systemTask;
    }

    public Boolean getSynchronous() {
        return synchronous;
    }

    public void setSynchronous(Boolean synchronous) {
        this.synchronous = synchronous;
    }

    public Map<String, InputParameter> getInputParams() {
        return inputParams;
    }

    public void setInputParams(Map<String, InputParameter> inputParams) {
        this.inputParams = inputParams;
    }

    public Map<String, OutputParameter> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(Map<String, OutputParameter> outputParams) {
        this.outputParams = outputParams;
    }

    public Map<String, InputParameter> getExecutorParams() {
        return executorParams;
    }

    public void setExecutorParams(Map<String, InputParameter> executorParams) {
        this.executorParams = executorParams;
    }
}
