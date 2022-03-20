package com.orch.core.service.metadata;

import com.orch.common.metadata.Task;
import com.orch.common.runtime.Job;

public interface TaskService {

    //不能创建则抛出异常
    Job createJobForTask(Task task);

    Task createTask(Task task);

    Task getTaskById(Integer taskId);

    Task getTaskByName(Integer workflowDefId, String taskName);

//
}
