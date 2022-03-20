package com.orch.core.service.utils.id;

import java.util.concurrent.locks.ReentrantLock;

public class SingleIdGenerator implements IdGenerator<Long>{

    private static final int SEQ_BIT_COUNT = 12;

    public SingleIdGenerator(int mostPerMillis) {
        if(mostPerMillis > (1 << SEQ_BIT_COUNT))
            mostPerMillis = 1 << SEQ_BIT_COUNT;
        this.mostPerMillis = mostPerMillis;
        this.seq = 0;
    }

    public SingleIdGenerator(){
        this(1 << SEQ_BIT_COUNT);
    }

    private int seq;

    private int mostPerMillis;

    @Override
    public synchronized Long generateId() {
        if(seq == mostPerMillis)
            seq = 0;
        return (System.currentTimeMillis() << SEQ_BIT_COUNT) + seq;
    }
}
