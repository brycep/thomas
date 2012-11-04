package com.servolabs.robolectric;

import org.junit.runners.model.InitializationError;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Robolectric test runner that will bind custom shadows.
 * <p>
 * See http://robolectric.blogspot.com/2011/01/how-to-create-your-own-shadow- classes.html
 */
public class ThomasTestRunner extends RobolectricTestRunner {

    public ThomasTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowCheckableListView.class);
        Robolectric.bindShadowClass(ShadowFragmentActivityWithActionBar.class);
        Robolectric.bindShadowClass(ShadowLoader.class);
    }

}