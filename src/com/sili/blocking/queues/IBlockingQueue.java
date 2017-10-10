package com.sili.blocking.queues;

/**
 * Created by leoz on 10/8/17.
 */
public interface IBlockingQueue<T> {
    void push(T item);

    T pop() throws Exception;

    boolean isEmpty();
}
