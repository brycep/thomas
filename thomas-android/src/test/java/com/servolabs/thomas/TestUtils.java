package com.servolabs.thomas;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class TestUtils {

    private TestUtils() {
    }

    /**
     * Based on http://stackoverflow.com/questions/11333354/how-can-i-test-fragments-with-robolectric/12903280.
     */
    public static void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = new FragmentActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    public static Matcher<Long> equalToWithinTolerance(final Long testValue, final Long tolerance) {
        return new BaseMatcher<Long>() {
            protected Long theTestValue = testValue;

            @Override
            public boolean matches(Object theExpected) {
                return Math.abs(((Long) theExpected) - theTestValue) < 1000;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(theTestValue.toString());
            }
        };
    }

}
