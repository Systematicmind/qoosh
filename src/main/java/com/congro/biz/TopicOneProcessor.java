package com.congro.biz;

import com.congoro.congops.OpContext;
import com.congoro.congops.OpPerformException;
import com.congro.data.EventBody;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
public class TopicOneProcessor extends QueuePollProcessor<EventBody> {

    private BufferedWriter bufferedWriter;

    public TopicOneProcessor() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("c:\\qoosh.txt",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public List<EventBody> perform(OpContext opContext) throws OpPerformException {
        //TODO perform the required operations on the rawData
        for (EventBody eventBody : getRawData()) {
            try {
                bufferedWriter.write(eventBody + "\r\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // returns the successfully processed items
    }

}
