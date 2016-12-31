package com.congro.ws;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Amir on 12/18/2016.
 */
@RestController
public class EventController {
    @RequestMapping(method = RequestMethod.POST, value = "/event")
    public void issueEvent() {

    }
}
