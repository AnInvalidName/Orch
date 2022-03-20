package com.orch.core.service.metadata;

import com.orch.common.metadata.TaskDef;

public interface TaskDefService {

    TaskDef createTaskDef(TaskDef taskDef);

    TaskDef updateTaskDef(TaskDef taskDef);

    TaskDef getTaskDefById(Integer taskId);

    TaskDef getTaskDefByName(String name, Integer version);

    Boolean deleteTaskDefById(Integer taskId);

    Boolean deleteTaskDefByName(String name, Integer version, Boolean all);

}
