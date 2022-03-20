package com.orch.common.runtime;

import java.util.Map;

public class JobProxy implements JobInfo{
    private Job target;

    public JobProxy(Job job){
        this.target = job;
    }

    @Override
    public Integer getId() {
        return target.getId();
    }

    @Override
    public Integer getWorkflowInstanceId() {
        return target.getWorkflowInstanceId();
    }

    @Override
    public Integer getTaskId() {
        return target.getTaskId();
    }

    @Override
    public String getTaskName() {
        return target.getTaskName();
    }

    @Override
    public Map<String, Object> getInputData() {
        return target.getInputData();
    }

    @Override
    public Map<String, Object> getExecutorData() {
        return target.getExecutorData();
    }

    @Override
    public Integer getRetryTimes() {
        return target.getRetryTimes();
    }
}
