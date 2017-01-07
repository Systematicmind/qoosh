package com.congro.ws;

import com.congro.biz.*;
import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by Amir on 12/18/2016.
 */
@RestController
public class EventController {

    private QueuePushManager<EventBody> queuePushManager;

    @Autowired
    private QueueManagerFactory queueManagerFactory;

    @PostConstruct
    protected void init() {
        queuePushManager = queueManagerFactory.getPushQueue(QueueManagerFactory.KAFKA_QUEUE,MyConst.TOPIC_ONE_NAME);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/event")
    public void issueEvent(@RequestBody EventBody eventBody) {
        queuePushManager.insertInQueue(eventBody);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event/{text}")
    public String echo(@PathVariable("text") String text) {
        return text;
    }
}
