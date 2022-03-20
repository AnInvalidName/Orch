package com.orch.core.dao;

import com.orch.common.metadata.WorkflowDef;
import com.orch.common.runtime.WorkflowInstance;

public interface WorkflowInstanceDAO {
    WorkflowInstance createWorkflowInstance(WorkflowInstance workflowInstance);

    WorkflowInstance getWorkflowInstanceById(Integer id);

    Boolean needIdGenerator();
}
