package com.orch.common.runtime;

import java.util.List;
import java.util.Map;

/**
 * 由于taskDef、workflowDef的task修改时，已开启的实例仍使用旧的版本，故需在job对象中保存必要的快照信息。
 */
public class Job implements JobInfo{
    public enum JobStatus {
        UNEXECUTED,
        EXECUTING,
        WAITING,
        SUSPENDED,
        COMPLETED,
        FAILED
    }
    //主键
    private Integer id;
    private Integer workflowInstanceId;
    private Integer taskId;
    private String taskName;
    //暂时无用
    private String taskDefId;
    private String taskDefType;
    private Boolean synchronous;
    //数据
    private JobStatus jobStatus;
    private Map<String, Object> inputData;
    private Map<String, Object> outputData;
    private Map<String, Object> executorData;
    private List<Integer> nextTaskIds;
    //补充信息
    private Integer retryTimes;
    private Long startTime;
    private Long endTime;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(Integer workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    @Override
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(String taskDefId) {
        this.taskDefId = taskDefId;
    }

    public String getTaskDefType() {
        return taskDefType;
    }

    public void setTaskDefType(String taskDefType) {
        this.taskDefType = taskDefType;
    }

    public Boolean getSynchronous() {
        return synchronous;
    }

    public void setSynchronous(Boolean synchronous) {
        this.synchronous = synchronous;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    @Override
    public Map<String, Object> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, Object> inputData) {
        this.inputData = inputData;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, Object> outputData) {
        this.outputData = outputData;
    }

    @Override
    public Map<String, Object> getExecutorData() {
        return executorData;
    }

    public void setExecutorData(Map<String, Object> executorData) {
        this.executorData = executorData;
    }

    public List<Integer> getNextTaskIds() {
        return nextTaskIds;
    }

    public void setNextTaskIds(List<Integer> nextTaskIds) {
        this.nextTaskIds = nextTaskIds;
    }

    @Override
    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
