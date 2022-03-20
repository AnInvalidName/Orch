package com.orch.common.metadata;


import java.util.List;
import java.util.Map;


public class WorkflowDef {
    private Integer id;
    private String name;
    private String description;
    private Integer version;
    private Boolean synchronous;
    /**
     * 定义工作流输入参数名、参数类型
     */
    private Map<String, InputParameter> inputParams;
    /**
     * 定义工作流输出参数名、数据来源
     */
    private Map<String, OutputParameter> outputParams;
    private String startTaskName;
    private List<Task> tasks;
}
