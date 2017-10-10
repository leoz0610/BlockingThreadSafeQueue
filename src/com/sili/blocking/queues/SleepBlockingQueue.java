package com.sili.blocking.queues;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

/**
 * Created by leoz on 10/8/17.
 */
class SleepBlockingQueue<T> implements IBlockingQueue<T> {
    private LinkedList<T> list;

    private Lock lock;

    @Override
    public void push(T item) {

    }

    @Override
    public T pop() throws Exception {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
