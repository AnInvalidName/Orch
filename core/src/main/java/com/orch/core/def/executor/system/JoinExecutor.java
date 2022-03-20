package com.orch.core.def.executor.system;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Executor;
import com.orch.common.runtime.Job;
import com.orch.common.runtime.JobInfo;

import java.util.Optional;

public class JoinExecutor implements Executor {
    @Override
    public void execute(JobInfo jobInfo, ExecutionResult executionResult) {
        /**
         * 并行分支合并节点
         * 执行参数JOIN_COUNT 记录要合并的分支数
         * 输出字段 currentBranchCount 记录已经到达的分支数
         * TODO 并发问题？？
         */
        Job job = (Job)jobInfo;
        Integer currentBranchCount = (Integer)job.getOutputData().get("currentBranchCount");
        if(currentBranchCount == null)
            currentBranchCount = 0;
        currentBranchCount++;
        executionResult.getOutputData().put("currentBranchCount", currentBranchCount);
        Integer JOIN_COUNT = (Integer)job.getExecutorData().get("JOIN_COUNT");
        if(currentBranchCount < JOIN_COUNT)
            job.setNextTaskIds(null);
        executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

    @Override
    public boolean isSystemTaskExecutor() {
        return true;
    }

}
