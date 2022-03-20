package com.orch.core.def.executor.system;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Executor;
import com.orch.common.runtime.JobInfo;
import com.orch.core.service.runtime.WorkflowInstanceService;

public class ExceptionExecutor implements Executor {

    private WorkflowInstanceService workflowInstanceService;
    @Override
    public void execute(JobInfo jobInfo, ExecutionResult executionResult) {
        String reason = (String)jobInfo.getInputData().get("reason");
        workflowInstanceService.failWorkflowInstance(jobInfo.getWorkflowInstanceId(), reason);
        executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }
    @Override
    public boolean isSynchronous() {
        return true;
    }

    @Override
    public boolean isSystemTaskExecutor() {
        return true;
    }
}
