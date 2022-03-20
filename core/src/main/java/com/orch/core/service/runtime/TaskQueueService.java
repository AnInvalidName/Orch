package com.orch.core.service.runtime;

public interface TaskQueueService {
    TaskQueueElement popTaskFromQueue();

    void pushTaskToQueue(TaskQueueElement taskQueueElement);
}
