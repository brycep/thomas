package com.servolabs.robolectric;

import android.support.v4.content.Loader;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@Implements(Loader.class)
public class ShadowLoader<D> {

    public boolean reset = false;
    public boolean started = true;
    public boolean forceLoaded = false;
    public boolean cancelled = false;
    public D deliveredResult = null;

    @Implementation
    public boolean isReset()  {
        return reset;
    }

    @Implementation
    public void reset()  {
        reset = true;
    }

    @Implementation
    void deliverResult(D deliveredData)  {
        this.deliveredResult = deliveredData;
    }

    @Implementation
    boolean isStarted()  {
        return started;
    }

    @Implementation
    void forceLoad()  {
        forceLoaded = true;
    }

    @Implementation
    void cancelLoad()  {
        cancelled = true;
    }

    @Implementation
    void onReset() {
        reset = true;
    }

}
