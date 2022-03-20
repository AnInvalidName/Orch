package com.orch.core.service.utils.lock;

public class LockService {

    private Lock lock;

    public LockService(){}

    public LockService(Lock lock){
        this.lock = lock;
    }

    public static String INSTANCE_PREFIX = "LOCK_WORKFLOW_INSTANCE_";

    public void acquireLockForWorkflowInstance(Integer workflowInstanceId){
        lock.acquireLock(INSTANCE_PREFIX + workflowInstanceId);
    }

    public void releaseLockForWorkflowInstance(Integer workflowInstanceId){
        lock.releaseLock(INSTANCE_PREFIX + workflowInstanceId);
    }

    public void deleteLockForWorkflowInstance(Integer workflowInstanceId){
        lock.releaseLock(INSTANCE_PREFIX + workflowInstanceId);
    }

}
