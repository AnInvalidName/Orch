package com.orch.core.service.thread;

import com.orch.core.service.execute.ExecutorService;
import com.orch.core.service.metadata.TaskService;
import com.orch.core.service.runtime.JobService;
import com.orch.core.service.runtime.TaskQueueService;
import com.orch.core.service.runtime.WorkflowInstanceService;
import com.orch.core.service.utils.lock.LockService;

import java.util.concurrent.*;

public class ThreadPoolService {

    private static final int DEFAULT_EXECUTOR_THREAD_COUNT = Runtime.getRuntime().availableProcessors();


    private ExecutorService executorService;
    private TaskQueueService taskQueueService;
    private JobService jobService;
    private TaskService taskService;
    private WorkflowInstanceService workflowInstanceService;
    private LockService lockService;

    private ThreadPoolExecutor threadPool;

    public ThreadPoolService(){
        this(Runtime.getRuntime().availableProcessors());
    }

    public ThreadPoolService(int executorThreadCount){
        this(
                new ThreadPoolExecutor(
                    executorThreadCount+1,
                    executorThreadCount+1,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>()
                )
        );
    }

    public ThreadPoolService(ThreadPoolExecutor threadPool){
        this.threadPool = threadPool;
        init();
    }

    private void init(){
        ExecutorThread.executorService = executorService;
        ExecutorThread.jobService = jobService;
        ExecutorThread.lockService = lockService;
        ExecutorThread.taskService = taskService;
        ExecutorThread.workflowInstanceService = workflowInstanceService;

        DistributeThread distributeThread = new DistributeThread();
        distributeThread.setTaskQueueService(taskQueueService);
        distributeThread.setThreadPool(threadPool);
        threadPool.execute(distributeThread);
    }
}
