package com.congro.biz;

import com.congro.data.EventBody;

/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
public interface QueuePushManager<T> {
    void insertInQueue(T eventBody);
}
