package com.congro.biz;

import com.congoro.congops.OpContext;
import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */

@Component("topicOneExecutor")
public class TopicOneQueuePollExecutor extends QueuePollExecutor<EventBody> {

    @Autowired
    QueuePollService<EventBody> queuePollService;

    @Autowired
    private QueueManagerFactory queueManagerFactory;

    @Autowired
    private QueueConfigBuilder queueConfigBuilder;

    //    this is a common OpContext for every Op base processor which is being run in its own thread
    OpContext opContext = new OpContext();

    @Override
    protected short getThreadNumber() {
        return queueConfigBuilder.getConsumerThreadNumber(MyConst.TOPIC_ONE_NAME);
    }

    @Override
    protected short getThreadDelay() {
        return queueConfigBuilder.getConsumerThreadDelay(MyConst.TOPIC_ONE_NAME);
    }

    @Override
    protected short getThreadInterval() {
        return queueConfigBuilder.getConsumerThreadInterval(MyConst.TOPIC_ONE_NAME);
    }

    @Override
    protected QueueProcessPipeline<EventBody> getNewQueueProcessPipeline() {
        return new QueueProcessPipeline(QueueManagerFactory.KAFKA_QUEUE,
                MyConst.TOPIC_ONE_NAME,
                queuePollService,
                new TopicOneProcessor(),
                opContext,
                queueManagerFactory,
                queueConfigBuilder.getConsumerPollWait(MyConst.TOPIC_ONE_NAME));
    }
}
