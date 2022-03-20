package com.orch.common.runtime;



import java.util.Map;

public interface JobInfo {

    Integer getId();

    Integer getWorkflowInstanceId();

    Integer getTaskId();

    String getTaskName();

    Map<String, Object> getInputData();

    Map<String, Object> getExecutorData();

    Integer getRetryTimes();

}
