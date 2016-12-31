package com.congro.ws;

import com.congro.biz.QueueManager;
import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Amir on 12/18/2016.
 */
@RestController
public class EventController {

    @Autowired
    private QueueManager queueManager;

    @RequestMapping(method = RequestMethod.POST, value = "/event")
    public void issueEvent(@RequestBody EventBody eventBody) {
        queueManager.insertInQueue(eventBody);
    }
}
