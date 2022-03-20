package com.orch.core.service.execute;

import com.orch.common.runtime.Executor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ExecutorServiceImpl implements ExecutorService{

    private Map<String, Supplier<Executor>> map;

    @Override
    public void registerExecutorSupplierForType(String taskType, Supplier<Executor> executorSupplier) {
        map.put(taskType, executorSupplier);
    }

    @Override
    public void registerExecutorForType(String taskType, Executor executor) {
        map.put(taskType, () -> executor);
    }

    @Override
    public Executor getExecutorForType(String type) {
        Supplier<Executor> supplier = map.get(type);
        return supplier == null ? null : supplier.get();
    }
}
