package com.orch.core.dao;

import com.orch.common.runtime.Job;

public interface JobDAO {
    void updateJobStatus(Integer jobId, Job.JobStatus status);

    void updateJob(Job job);

    Job getJobById(Integer id);

    Job createJob(Job job);

    Boolean needIdGenerator();
}
