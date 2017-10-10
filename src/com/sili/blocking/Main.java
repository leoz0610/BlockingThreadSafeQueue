package com.sili.blocking;

import com.sili.blocking.jobs.ConsumerJob;
import com.sili.blocking.jobs.ProducerJob;
import com.sili.blocking.queues.IBlockingQueue;
import com.sili.blocking.queues.IQueueFactory;
import com.sili.blocking.queues.QueueType;
import com.sili.blocking.queues.ThreadSafeQueueFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        IQueueFactory<Integer> queueFactory = new ThreadSafeQueueFactory<>();
        IBlockingQueue<Integer> queue = queueFactory.create(QueueType.Synchronized);

        List<Thread> jobs = new ArrayList<>();

        int producerCnt = 3;
        int consumerCnt = 2;

        for (int i = 0; i < producerCnt; i++) {
            jobs.add(new Thread(new ProducerJob(queue, "P" + i)));
        }

        for (int i = 0; i < consumerCnt; i++) {
            jobs.add(new Thread(new ConsumerJob(queue, "C" + i)));
        }

        for (Thread j : jobs) {
            j.start();
        }

        System.in.read();
    }
}
