package com.orch.common.runtime;


import java.util.List;
import java.util.Map;

public class ExecutionResult {

    public enum ExecutionStatus {
        SUCCESSFUL,
        FAILED,
        RETRY
    }

    private Integer jobId;
    private ExecutionStatus status;
    private Map<String, Object> outputData;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, Object> outputData) {
        this.outputData = outputData;
    }

}
