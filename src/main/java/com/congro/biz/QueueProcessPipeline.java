package com.congro.biz;

import com.congoro.congops.OPS;
import com.congoro.congops.Op;
import com.congoro.congops.OpContext;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
public class QueueProcessPipeline<T> implements Runnable {

    private QueuePollService<T> queuePollService;
    private QueuePoolProcessor<T> processor;
    private QueuePollManager<T> queuePollManager;
    private OpContext opContext;
    private String topicName;
    private String queueName;

    public QueueProcessPipeline(String queueName, String topicName,
                                QueuePollService<T> queuePollService, QueuePoolProcessor<T> processor, OpContext opContext) {
        this.queuePollService = queuePollService;
        this.processor = processor;
        this.opContext = opContext;
        this.topicName = topicName;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        if (queuePollManager == null) {
            queuePollManager = QueueManagerFactory.getInstance().getQueue(queueName,topicName);
        }
        processor.setRawData(queuePollManager.readFromQueue());
        try {
            queuePollService.process(processor,opContext);
            queuePollManager.commitOffset();
        } catch (Throwable e) {
            queuePollManager.closeQueue();
            queuePollManager = QueueManagerFactory.getInstance().getQueue(queueName,topicName);
        }
    }
}
