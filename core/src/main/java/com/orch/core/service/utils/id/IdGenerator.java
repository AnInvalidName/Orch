package com.orch.core.service.utils.id;

import java.util.concurrent.locks.Lock;

public interface IdGenerator<T> {
    T generateId();
}
