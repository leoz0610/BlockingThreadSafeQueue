package com.sili.blocking.queues;

/**
 * Created by leoz on 10/8/17.
 */
public interface IQueueFactory<T> {
    IBlockingQueue<T> create(QueueType type);
}
