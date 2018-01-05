package com.sili.blocking.queues;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * Created by leoz on 10/8/17.
 */
@ThreadSafe
class SynchronizedBlockingQueue<T> implements IBlockingQueue<T> {
    @GuardedBy("this")
    private final LinkedList<T> list;
    private final int maxCapacity;

    SynchronizedBlockingQueue(int maxCapacity) {
        list = new LinkedList<>();
        this.maxCapacity = maxCapacity;
    }

    @Override
    public synchronized void push(T item) {
        if (list.size() >= maxCapacity) {
            throw new IllegalStateException("Over max capacity.");
        }

        list.addLast(item);
    }

    @Override
    public synchronized T pop() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }

        return list.removeFirst();
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
}
