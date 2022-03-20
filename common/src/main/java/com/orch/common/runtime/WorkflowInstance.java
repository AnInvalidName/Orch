package com.orch.common.runtime;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Map;

public class WorkflowInstance {
    public enum WorkflowStatus {
        RUNNING,
        COMPLETED,
        FAILED,
        TERMINATED,
        PAUSED
    }
    private Integer id;
    private Integer workflowDefId;
    private WorkflowStatus workflowStatus;

    private Map<String, Object> outputData;
    private Map<String, Object> inputData;
    private Map<String, Object> globalData;
    private List<Job> jobs;
    private Integer startTaskId;

    //临时方案
    private Boolean success;
    private String explain;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkflowDefId() {
        return workflowDefId;
    }

    public void setWorkflowDefId(Integer workflowDefId) {
        this.workflowDefId = workflowDefId;
    }

    public WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, Object> outputData) {
        this.outputData = outputData;
    }

    public Map<String, Object> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, Object> inputData) {
        this.inputData = inputData;
    }

    public Map<String, Object> getGlobalData() {
        return globalData;
    }

    public void setGlobalData(Map<String, Object> globalData) {
        this.globalData = globalData;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void setStartTaskId(Integer startTaskId) {
        this.startTaskId = startTaskId;
    }

    public Integer getStartTaskId() {
        return startTaskId;
    }

    public void setStartTaskName(Integer startTaskId) {
        this.startTaskId = startTaskId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
