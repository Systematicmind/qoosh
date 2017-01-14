package com.congro.biz;

import com.congoro.congops.OpContext;


/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
public class QueueProcessPipeline<T> implements Runnable {

    private QueuePollService<T> queuePollService;
    private QueuePollProcessor<T> processor;
    private QueuePollManager<T> queuePollManager;
    private OpContext opContext;
    private String topicName;
    private String queueName;
    private QueueManagerFactory queueManagerFactory;
    private int pollWait = 1000;

    public QueueProcessPipeline(String queueName, String topicName,
                                QueuePollService<T> queuePollService,
                                QueuePollProcessor<T> processor,
                                OpContext opContext,
                                QueueManagerFactory queueManagerFactory) {
        this.queuePollService = queuePollService;
        this.processor = processor;
        this.opContext = opContext;
        this.topicName = topicName;
        this.queueName = queueName;
        this.queueManagerFactory = queueManagerFactory;
    }

    public QueueProcessPipeline(String queueName, String topicName,
                                QueuePollService<T> queuePollService,
                                QueuePollProcessor<T> processor,
                                OpContext opContext,
                                QueueManagerFactory queueManagerFactory,
                                int pollWait) {
        this.queuePollService = queuePollService;
        this.processor = processor;
        this.opContext = opContext;
        this.topicName = topicName;
        this.queueName = queueName;
        this.queueManagerFactory = queueManagerFactory;
        this.pollWait = pollWait;
    }

    @Override
    public void run() {
        System.out.println("QueueProcessPipeline--> polling the queue just started.");
        if (queuePollManager == null) {
            queuePollManager = queueManagerFactory.getPollQueue(queueName, topicName);
        }
        try {
            processor.setRawData(queuePollManager.readFromQueue(pollWait));
            queuePollService.process(processor,opContext);
            queuePollManager.commitOffset();
        } catch (Throwable e) {
            System.out.println("QueueProcessPipeline--> polling or processing the queue failed!!!");
            queuePollManager.closeQueue();
            System.out.println("QueueProcessPipeline--> a new queue is about to be created.");
            queuePollManager = queueManagerFactory.getPollQueue(queueName, topicName);
        }
    }
}
