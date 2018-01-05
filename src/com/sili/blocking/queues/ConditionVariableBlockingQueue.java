package com.sili.blocking.queues;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leoz on 1/3/18.
 */
@ThreadSafe
class ConditionVariableBlockingQueue<T> implements IBlockingQueue<T> {
    @GuardedBy("exLock")
    private final LinkedList<T> list;
    private final int maxCapacity;
    private final Lock exLock;
    private final int maxWaitTime = 10;
    private final Condition notFull;
    private final Condition notEmpty;

    ConditionVariableBlockingQueue(int maxCapacity) {
        list = new LinkedList<>();
        this.maxCapacity = maxCapacity;
        exLock = new ReentrantLock();
        notFull = exLock.newCondition();
        notEmpty = exLock.newCondition();
    }

    @Override
    public void push(T item) throws Exception {
        try {
            exLock.lock();

            while (list.size() >= maxCapacity) {
                if (!notFull.await(maxWaitTime, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Failed to push item");
                }
            }

            /*
            if (list.size() >= maxCapacity) {
                throw new IllegalStateException("Invalid: list size is over max capacity");
            }
            */

            list.addLast(item);
            notEmpty.signal();
        } finally {
            exLock.unlock();
        }
    }

    @Override
    public T pop() throws Exception {
        try {
            exLock.lock();

            while (list.isEmpty()) {
                if (!notEmpty.await(maxWaitTime, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Failed to pop item");
                }
            }

            /*
            if (list.isEmpty()) {
                throw new IllegalStateException("Invalid: list size is empty");
            }
            */

            T item = list.removeFirst();
            notFull.signal();
            return item;
        } finally {
            exLock.unlock();
        }
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
