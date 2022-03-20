package com.orch.core.def.executor.task;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.JobInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class TestExecutor {
    //private TestService testService;
    protected ExecutionResult doExecute(JobInfo jobInfo) throws Exception{
//        String methodName = (String) jobInfo.getInputParams("methodName");
//        Map<String, Object> payload = (Map<String, Object>) jobInfo.getInputParams("payload");
//        Map<String, Object> output = jobInfo.getOutputParams();
//
//        Class[] clazz = new Class[payload.size()];
//        for(int i = 0; i != clazz.length; i++)
//            clazz[i] = Object.class;
//
//        Method m = testService.getClass().getMethod(methodName, clazz);
//
//        Parameter[] parameters = m.getParameters();
//        Object[] params = new Object[payload.size()];
//        for(int i = 0; i != parameters.length; i++){
//            params[i] = payload.get(parameters[i].getName());
//        }
//        Object result = m.invoke(testService, params);
//        Class returnType = m.getReturnType();
//        for(Map.Entry<String, Object> entry : output.entrySet()){
//            Field field = returnType.getField(entry.getKey());
//            entry.setValue(field.get(result));
//        }
        ExecutionResult executionResult = new ExecutionResult();
        return executionResult;
    }
}
