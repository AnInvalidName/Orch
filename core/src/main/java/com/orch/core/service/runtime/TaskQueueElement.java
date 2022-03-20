package com.orch.core.service.runtime;

public class TaskQueueElement{
    private Integer workflowInstanceId;
    private Integer taskId;

    public TaskQueueElement(Integer workflowInstanceId, Integer taskId) {
        this.workflowInstanceId = workflowInstanceId;
        this.taskId = taskId;
    }

    public Integer getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(Integer workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
