package com.congro.biz;

import com.congro.data.EventBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Amir on 12/19/2016.
 */
public class EventTransferHub implements Runnable {

    @Autowired
    private QueueManager queueManager;

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("c:\\qoosh.txt",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        while (true) {
            List<EventBody> eventBodies = queueManager.readFromQueue();
            if (eventBodies.size() > 0) {
                try {
                    bufferedWriter.write("**************** KAFKA Client's working very well! ******************");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
