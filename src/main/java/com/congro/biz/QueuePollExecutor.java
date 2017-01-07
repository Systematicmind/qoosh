package com.congro.biz;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Created by Amir Hajizadeh on 1/3/2017.
 */

public abstract class QueuePollExecutor<T>  {

    private ScheduledExecutorService  executorService;

    public void execute() {
        executorService = Executors.newScheduledThreadPool(getThreadNumber());
        for (int i = 0; i < getThreadNumber(); i++) {
            executorService.scheduleWithFixedDelay(getNewQueueProcessPipeline(), getThreadDelay(), getThreadInterval(), TimeUnit.SECONDS);
        }
    }

    @PreDestroy
    protected void preDestroy() {
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        } finally {
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
                System.out.println("executorService forced to shutdown!");
            }
        }
    }

    protected abstract short getThreadNumber();

    protected abstract short getThreadDelay();

    protected abstract short getThreadInterval();

    protected abstract QueueProcessPipeline<T> getNewQueueProcessPipeline();

}
