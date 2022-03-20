package com.orch.common.metadata;

import java.util.List;
import java.util.Map;

/**
 * 创建工作流时定义的工作流任务
 * 制定任务所要执行的TaskDef、每个参数的值或引用、下一个要执行的工作流任务名
 * 为了方便，需要覆盖TaskDef的所有必要字段
 * 唯一标识<id>  <workflowDefId, taskName>
 */
public class Task {
    private Integer id;
    private String taskName;
    private Integer workflowDefId;
    private Integer taskDefId;
    //TaskDef冗余信息
    private String taskDefName;
    private Integer taskDefVersion;
    private String taskDefType;
    private Boolean systemTask;
    private Boolean synchronous;
    //Task必要信息
    private Map<String, InputParameter> inputData;
    private Map<String, OutputParameter> outputData;
    private Map<String, InputParameter> executorData;
    private List<PushParameter> pushParams;
    private List<String> nextTaskNames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getWorkflowDefId() {
        return workflowDefId;
    }

    public void setWorkflowDefId(Integer workflowDefId) {
        this.workflowDefId = workflowDefId;
    }

    public String getTaskDefName() {
        return taskDefName;
    }

    public void setTaskDefName(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    public Integer getTaskDefVersion() {
        return taskDefVersion;
    }

    public void setTaskDefVersion(Integer taskDefVersion) {
        this.taskDefVersion = taskDefVersion;
    }

    public Integer getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(Integer taskDefId) {
        this.taskDefId = taskDefId;
    }

    public String getTaskDefType() {
        return taskDefType;
    }

    public void setTaskDefType(String taskDefType) {
        this.taskDefType = taskDefType;
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

    public Map<String, InputParameter> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, InputParameter> inputData) {
        this.inputData = inputData;
    }

    public Map<String, OutputParameter> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, OutputParameter> outputData) {
        this.outputData = outputData;
    }

    public Map<String, InputParameter> getExecutorData() {
        return executorData;
    }

    public void setExecutorData(Map<String, InputParameter> executorData) {
        this.executorData = executorData;
    }

    public List<PushParameter> getPushParams() {
        return pushParams;
    }

    public void setPushParams(List<PushParameter> pushParams) {
        this.pushParams = pushParams;
    }

    public List<String> getNextTaskNames() {
        return nextTaskNames;
    }

    public void setNextTaskNames(List<String> nextTaskNames) {
        this.nextTaskNames = nextTaskNames;
    }
}
