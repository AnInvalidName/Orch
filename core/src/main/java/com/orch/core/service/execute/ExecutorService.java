package com.orch.core.service.execute;

import com.orch.common.runtime.Executor;

import java.util.function.Supplier;

public interface ExecutorService {

    void registerExecutorSupplierForType(String taskType, Supplier<Executor> executorSupplier);

    void registerExecutorForType(String taskType, Executor executor);

    Executor getExecutorForType(String type);

}
