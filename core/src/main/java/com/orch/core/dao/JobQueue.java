package com.orch.core.dao;

public interface JobQueue {

    Integer pop();

    void push(Integer jobId);

    Boolean needIdGenerator();
}
