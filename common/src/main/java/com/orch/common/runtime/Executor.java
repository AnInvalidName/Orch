package com.orch.common.runtime;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Job;

public interface Executor{
    void execute(JobInfo jobInfo, ExecutionResult executionResult);
    default boolean isSynchronous(){ return true; }
    default boolean isSystemTaskExecutor(){return false;}
}
