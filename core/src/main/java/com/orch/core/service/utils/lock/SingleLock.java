package com.orch.core.service.utils.lock;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SingleLock implements Lock{

    private Map<String, ReentrantLock> map;

    @Override
    public void acquireLock(String lockId) {
        ReentrantLock lock = map.get(lockId);
        if(lock == null){
            lock = new ReentrantLock();
            map.put(lockId, lock);
        }
        lock.lock();
    }

    @Override
    public boolean acquireLock(String lockId, long timeToTry, TimeUnit unit) {
        ReentrantLock lock = map.get(lockId);
        if(lock == null){
            lock = new ReentrantLock();
            map.put(lockId, lock);
        }
        boolean success = true;
        try{
            success = lock.tryLock(timeToTry, unit);
        }catch (Exception e){
            success = false;
        }
        return success;
    }
    @Override
    public void releaseLock(String lockId) {
        Optional.ofNullable(map.get(lockId)).ifPresent(ReentrantLock::unlock);
    }

    @Override
    public void deleteLock(String lockId) {
        map.remove(lockId);
    }
}
