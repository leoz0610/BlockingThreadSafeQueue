package com.sili.blocking.jobs;

import com.sili.blocking.queues.IBlockingQueue;

/**
 * Created by leoz on 10/8/17.
 */
public class ConsumerJob extends JobBase<Integer> {
    public ConsumerJob(IBlockingQueue<Integer> queue, String jobName) {
        super(queue, jobName, JobRole.Consumer);
    }

    @Override
    public void run() {
        System.out.printf("Consumer thread %s starting...\n", getJobName());

        try {
            while (true) {
                try {
                    int p = queue.pop();
                    System.out.printf("Consumer thread %s consumed item %d.\n", getJobName(), p);
                } catch (IllegalStateException ie) {
                    System.out.printf("Consumer thread %s exception: %s.\n", getJobName(), ie);
                }

                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
    }
}
