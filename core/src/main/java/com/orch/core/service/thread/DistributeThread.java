package com.orch.core.service.thread;

import com.orch.core.service.runtime.TaskQueueElement;
import com.orch.core.service.runtime.TaskQueueService;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class DistributeThread implements Runnable{

    private ThreadPoolExecutor threadPool;

    private TaskQueueService taskQueueService;

    @Override
    public void run() {
        while(true){
            TaskQueueElement task = taskQueueService.popTaskFromQueue();
            if(task == null)
                continue;
            threadPool.execute(new ExecutorThread(task.getWorkflowInstanceId(), task.getTaskId()));
        }
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public TaskQueueService getTaskQueueService() {
        return taskQueueService;
    }

    public void setTaskQueueService(TaskQueueService taskQueueService) {
        this.taskQueueService = taskQueueService;
    }
}
