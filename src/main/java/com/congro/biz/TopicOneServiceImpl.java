package com.congro.biz;

import com.congoro.congops.OPS;
import com.congoro.congops.Op;
import com.congoro.congops.OpContext;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
@Service
public class TopicOneServiceImpl<T> implements QueuePollService<T> {


    @Override
//    this method could be transactional
//    @Transactional
    public void process(Op<List<T>> processor, OpContext opContext) {
        OPS.perform(processor,opContext);
    }
}
