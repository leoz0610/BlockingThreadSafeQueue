package com.sili.blocking.queues;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leoz on 10/8/17.
 */
@ThreadSafe
class SleepBlockingQueue<T> implements IBlockingQueue<T> {
    @GuardedBy("exLock")
    private final LinkedList<T> list;
    private final int maxCapacity;
    private final Lock exLock;
    private final int maxRetries = 3;

    SleepBlockingQueue(int maxCapacity) {
        list = new LinkedList<T>();
        this.maxCapacity = maxCapacity;
        exLock = new ReentrantLock();
    }

    @Override
    public void push(T item) throws Exception {
        int retries = 0;

        do {
            exLock.lock();
            try {
                if (list.size() < maxCapacity) {
                    list.addLast(item);
                    return;
                }
            } finally {
                exLock.unlock();
            }

            Thread.sleep(100);
            retries++;
        } while (retries < maxRetries);

        throw new IllegalStateException("Failed to push item.");
    }

    @Override
    public T pop() throws Exception {
        int retries = 0;

        do {
            exLock.lock();
            try {
                if (!list.isEmpty()) {
                    return list.removeFirst();
                }
            } finally {
                exLock.unlock();
            }

            Thread.sleep(100);
            retries++;
        } while (retries < maxRetries);

        throw new IllegalStateException("Failed to pop item.");
    }

    @Override
    public boolean isEmpty() {
        try {
            exLock.lock();
            return list.isEmpty();
        } finally {
            exLock.unlock();
        }
    }
}
