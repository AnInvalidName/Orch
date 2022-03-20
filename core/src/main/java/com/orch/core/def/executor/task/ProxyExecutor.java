package com.orch.core.def.executor.task;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Executor;
import com.orch.common.runtime.JobInfo;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyExecutor implements Executor {

    public ProxyExecutor(Object serviceProxy) {
        this.serviceProxy = serviceProxy;
    }

    private boolean synchronous = true;

    private Object serviceProxy;

    private String methodNameParam = "methodNameParam";

    @Override
    public void execute(JobInfo jobInfo, ExecutionResult executionResult) {
        try {
            String methodName = (String) jobInfo.getExecutorData().get(methodNameParam);
            Map<String, Object> payload = jobInfo.getInputData();
            Class[] clazz = payload.values().stream()
                    .map(Object::getClass)
                    .collect(Collectors.toList())
                    .toArray(new Class[payload.size()]);
            Method m = serviceProxy.getClass().getMethod(methodName, clazz);
            Object[] params = payload.values().toArray(new Object[payload.size()]);
            Object result = m.invoke(serviceProxy, params);
            Class returnType = m.getReturnType();
            for(Map.Entry<String, Object> entry : executionResult.getOutputData().entrySet()){
                if("*".equals(entry.getKey()))
                    entry.setValue(result);
                else
                    entry.setValue(returnType.getField(entry.getKey()).get(result));
            }
            executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
        }catch (Exception e){
            executionResult.setStatus(ExecutionResult.ExecutionStatus.FAILED);
        }
    }

    @Override
    public boolean isSynchronous() {
        return synchronous;
    }

    @Override
    public boolean isSystemTaskExecutor() {
        return false;
    }

    public void setSynchronous(boolean synchronous) {
        this.synchronous = synchronous;
    }

    public void setServiceProxy(Object serviceProxy) {
        this.serviceProxy = serviceProxy;
    }

    public void setMethodNameParam(String methodNameParam) {
        this.methodNameParam = methodNameParam;
    }
}
