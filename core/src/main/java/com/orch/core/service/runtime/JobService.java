package com.orch.core.service.runtime;

import com.orch.common.metadata.Task;
import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Job;

public interface JobService {

    Job getJobById(Integer jobId);

    Job getJobByInstanceIdAndTaskName(Integer instanceId, String taskName);

    void updateJobStatusById(Integer jobId, Job.JobStatus jobStatus);

    void updateJob(Job job);

    void submitJobExecutionResult(Job job, ExecutionResult result);

    void submitJobExecutionResult(Job job, ExecutionResult result, Task task);

    void decideNextJob(Job job);

    Job createJob(Job job);

}
