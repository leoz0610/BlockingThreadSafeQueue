package com.sili.blocking.jobs;

import com.sili.blocking.queues.IBlockingQueue;

/**
 * Created by leoz on 10/8/17.
 */
public abstract class JobBase<T> implements Runnable {
    private String jobName;

    private JobRole role;

    protected IBlockingQueue<T> queue;

    public JobBase(IBlockingQueue<T> queue, String jobName, JobRole role) {
        this.queue = queue;
        this.jobName = jobName;
        this.role = role;
    }

    public String getJobName() {
        return jobName;
    }

    public JobRole getRole() {
        return role;
    }

    @Override
    public abstract void run();
}
