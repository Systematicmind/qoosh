package com.congro.biz;

import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Amir on 12/19/2016.
 */
@Component
public class EventTransferHub implements Runnable {

    @Autowired
    @Qualifier("topicOneExecutor")
    QueuePollExecutor<EventBody> queuePollExecutor;

    @Override
    public void run() {
        queuePollExecutor.execute();
    }
}
