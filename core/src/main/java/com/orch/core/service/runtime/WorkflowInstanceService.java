package com.orch.core.service.runtime;

import com.orch.common.runtime.WorkflowInstance;
import com.orch.common.runtime.WorkflowInstanceResult;

import java.util.Map;

public interface WorkflowInstanceService {

    WorkflowInstance createInstanceWithWorkflowDef(WorkflowInstance instance);

    WorkflowInstanceResult createSynchronousInstance(WorkflowInstance instance);

    void suspendWorkflowInstance(Integer workflowInstanceId);

    void continueWorkflowInstance(Integer workflowInstanceId);

    void terminateWorkflowInstance(Integer workflowInstanceId);

    void failWorkflowInstance(Integer workflowInstanceId, String reason);

    void finishWorkflowInstance(Integer workflowInstanceId);

    WorkflowInstance getWorkflowInstanceById(Integer workflowInstanceId);


    WorkflowInstance.WorkflowStatus getStatusOfWorkflowInstance(Integer workflowInstanceId);

    void updateStatusOfWorkflowInstance(Integer workflowInstanceId, WorkflowInstance.WorkflowStatus status);

    void createGlobalParamInWorkflowInstance(Integer workflowInstanceId, String paramName, Object value);

    Object getGlobalParamValue(Integer workflowInstance, String paramName);



    void getInputDataOfWorkflowInstance(Integer workflowInstanceId);

    void updateInputDataOfWorkflowInstance(Integer workflowInstanceId, Map<String, Object> data);
}
