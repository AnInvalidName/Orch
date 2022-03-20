package com.orch.core.def.executor.system;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Executor;
import com.orch.common.runtime.Job;
import com.orch.common.runtime.JobInfo;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;

public class ConditionExecutor implements Executor {
    @Override
    public void execute(JobInfo jobInfo, ExecutionResult executionResult) {
        String script = (String)jobInfo.getInputData().get("script");
        Map<String, Object> params = (Map)jobInfo.getInputData().get("paramSource");
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        params.forEach((paramName, value)-> binding.setVariable(paramName, value));
        Boolean choice = (Boolean)shell.evaluate(script);
        Job job = (Job)jobInfo;
        job.getNextTaskIds().remove(choice ? 1 : 0);
        executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
        executionResult.getOutputData().put("choice", job.getNextTaskIds().get(0));
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
