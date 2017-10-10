package com.sili.blocking.queues;

import java.util.LinkedList;

/**
 * Created by leoz on 10/8/17.
 */
class SynchronizedBlockingQueue<T> implements IBlockingQueue<T> {
    private LinkedList<T> list;

    SynchronizedBlockingQueue() {
        list = new LinkedList<>();
    }

    @Override
    public synchronized void push(T item) {
        list.add(item);
        System.out.println("Pushed " + item);
    }

    @Override
    public T pop() throws Exception {
        T p = null;
        boolean consumed = false;

        while (!consumed) {
            synchronized (this) {
                if (!list.isEmpty()) {
                    consumed = true;
                    p = list.removeFirst();
                }
            }

            if (!consumed) {
                Thread.sleep(100);
            }
        }

        System.out.println("Poped " + p);
        return p;
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
}
