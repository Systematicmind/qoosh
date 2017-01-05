package com.congro.biz;

import com.congoro.congops.Op;
import com.congro.data.EventBody;

import java.util.List;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
public abstract class QueuePoolProcessor<T> implements Op<List<T>> {

    private List<T> rawData;

    public List<T> getRawData() {
        return rawData;
    }

    public void setRawData(List<T> rawData) {
        this.rawData = rawData;
    }
}
