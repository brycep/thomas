/**
 * 
 */
package com.servolabs.robolectric;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowFragmentActivity;

/**
 * Extends ShadowFragmentActivity to add support for the ActionBar.
 */
@Implements(FragmentActivity.class)
public class ShadowFragmentActivityWithActionBar extends ShadowFragmentActivity {

    private ActionBar actionBar;

    @Implementation
    public ActionBar getActionBar() {
        if (actionBar == null) {
            actionBar = new TestActionBar(getApplicationContext());
        }
        return actionBar;
    }

}
