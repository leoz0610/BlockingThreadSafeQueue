package com.sili.blocking.queues;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by leoz on 1/4/18.
 */
@ThreadSafe
class SemaphoreBlockingQueue<T> implements IBlockingQueue<T> {
    @GuardedBy("this")
    private final LinkedList<T> list;
    private final Semaphore availableCounts;
    private final Semaphore occupiedCounts;

    SemaphoreBlockingQueue(int maxCapacity) throws Exception {
        list = new LinkedList<>();
        availableCounts = new Semaphore(maxCapacity, true);
        occupiedCounts = new Semaphore(maxCapacity, true);
        occupiedCounts.acquire(maxCapacity);
    }

    @Override
    public void push(T item) throws Exception {
        availableCounts.acquire();
        synchronized (this) {
            list.addLast(item);
        }
        occupiedCounts.release();
    }

    @Override
    public T pop() throws Exception {
        occupiedCounts.acquire();
        T item;
        synchronized (this) {
            item = list.removeFirst();
        }
        availableCounts.release();
        return item;
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
}
