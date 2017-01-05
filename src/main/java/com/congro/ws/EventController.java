package com.congro.ws;

import com.congro.biz.QueuePollManager;
import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Amir on 12/18/2016.
 */
@RestController
public class EventController {

    @Autowired
    private QueuePollManager queuePollManager;

    @RequestMapping(method = RequestMethod.POST, value = "/event")
    public void issueEvent(@RequestBody EventBody eventBody) {
        queuePollManager.insertInQueue(eventBody);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event/{text}")
    public String echo(@PathVariable("text") String text) {
        return text;
    }
}
