package com.congro.biz;

import com.congoro.congops.Op;
import com.congoro.congops.OpContext;

import java.util.List;

/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
public interface QueuePollService<T> {

    void process(Op<List<T>> processor, OpContext opContext);
}
