package com.orch.core.service.metadata;

import com.orch.common.metadata.WorkflowDef;

public interface WorkflowDefService {

    WorkflowDef createWorkflowDef(WorkflowDef workflowDef);

    WorkflowDef updateWorkflowDef(WorkflowDef workflowDef);

    WorkflowDef getWorkflowDefById(Integer workflowDefId);

    WorkflowDef getWorkflowDefByName(String workflowDefName, Integer version);

    Boolean deleteWorkflowDefById(Integer workflowDefId);

    Boolean deleteWorkflowDefByName(String workflowDefName, Integer version, Boolean all);

}
