package com.congro.biz;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
public class QueuePollProcessResult {

    private boolean ok;
    private long lastReadOffset;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public long getLastReadOffset() {
        return lastReadOffset;
    }

    public void setLastReadOffset(long lastReadOffset) {
        this.lastReadOffset = lastReadOffset;
    }
}
