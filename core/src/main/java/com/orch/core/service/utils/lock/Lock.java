package com.orch.core.service.utils.lock;

import java.util.concurrent.TimeUnit;

public interface Lock {
    void acquireLock(String lockId);
    boolean acquireLock(String lockId, long timeToTry, TimeUnit unit);
    void releaseLock(String lockId);
    void deleteLock(String lockId);
}
