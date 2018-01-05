package com.sili.blocking.queues;

import java.security.InvalidParameterException;

/**
 * Created by leoz on 10/8/17.
 */
public class ThreadSafeQueueFactory<T> implements IQueueFactory<T> {
    @Override
    public IBlockingQueue<T> create(QueueType type, int maxCapacity) throws Exception {
        switch (type) {
            case Synchronized:
                return new SynchronizedBlockingQueue<>(maxCapacity);
            case Sleep:
                return new SleepBlockingQueue<>(maxCapacity);
            case Condition:
                return new ConditionVariableBlockingQueue<>(maxCapacity);
            case Semaphore:
                return new SemaphoreBlockingQueue<>(maxCapacity);
            default:
                throw new InvalidParameterException();
        }
    }
}
