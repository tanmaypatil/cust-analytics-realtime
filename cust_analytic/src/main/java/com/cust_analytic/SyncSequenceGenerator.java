package com.cust_analytic;

import java.util.concurrent.atomic.AtomicLong;

public class SyncSequenceGenerator implements SequenceGenerator {
    private AtomicLong value = new AtomicLong(1);

    @Override
    public long getNext() {
        return value.getAndIncrement();
    }
    
}
