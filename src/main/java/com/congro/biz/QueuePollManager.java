package com.congro.biz;

import com.congro.data.EventBody;

import java.util.List;

/**
 * Created by Amir on 12/18/2016.
 */
public interface QueuePollManager<T> {

    List<T> readFromQueue(int pollWait);

    void commitOffset();

    void closeQueue();

}
