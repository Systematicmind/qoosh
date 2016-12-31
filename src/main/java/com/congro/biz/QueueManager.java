package com.congro.biz;

import com.congro.data.EventBody;

import java.util.List;

/**
 * Created by Amir on 12/18/2016.
 */
public interface QueueManager {
    void insertInQueue(EventBody eventBody);

    List<EventBody> readFromQueue();

    void commitOffset();

}
