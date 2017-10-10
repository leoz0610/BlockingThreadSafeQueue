package com.sili.blocking.queues;

import java.security.InvalidParameterException;

/**
 * Created by leoz on 10/8/17.
 */
public class ThreadSafeQueueFactory<T> implements IQueueFactory<T> {
    @Override
    public IBlockingQueue<T> create(QueueType type) {
        switch (type) {
            case Synchronized:
                return new SynchronizedBlockingQueue<T>();
            default:
                throw new InvalidParameterException();
        }
    }
}
