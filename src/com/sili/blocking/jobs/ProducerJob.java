package com.sili.blocking.jobs;

import com.sili.blocking.queues.IBlockingQueue;

import java.util.Random;

/**
 * Created by leoz on 10/8/17.
 */
public class ProducerJob extends JobBase<Integer> {
    private Random rand;

    public ProducerJob(IBlockingQueue<Integer> queue, String jobName) {
        super(queue, jobName, JobRole.Producer);
        rand = new Random();
    }

    @Override
    public void run() {
        System.out.printf("Producer thread %s starting...\n", getJobName());

        try {
            while (true) {
                try {
                    int p = rand.nextInt(100);
                    queue.push(p);
                    System.out.printf("Producer thread %s produced item %d.\n", getJobName(), p);
                } catch (IllegalStateException ie) {
                    System.out.printf("Producer thread %s exception: %s.\n", getJobName(), ie);
                }

                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
    }
}
