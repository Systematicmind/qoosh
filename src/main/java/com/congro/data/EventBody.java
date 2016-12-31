package com.congro.data;

import java.io.Serializable;

/**
 * Created by Amir on 12/18/2016.
 */
public class EventBody implements Serializable {
    private static final long serialVersionUID = -8331843909944553105L;

    private Integer eventType;
    private String hostURL;
    private String contentId;

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "EventBody{" +
                "eventType=" + eventType +
                ", hostURL='" + hostURL + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }
}
