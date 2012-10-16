package com.servolabs.robolectric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowListView;

/**
 * Extends the base Robolectric ShadowListView to add support for checkable items.
 * <p>
 * See http://robolectric.blogspot.com/2011/01/how-to-create-your-own-shadow- classes.html for the theory.
 */
@Implements(ListView.class)
public class ShadowCheckableListView extends ShadowListView {

    @SuppressLint("UseSparseArrays")
    private final Map<Integer, Boolean> checkedByPosition = new HashMap<Integer, Boolean>();

    @Override
    public void __constructor__(Context context) {
        super.__constructor__(context);
    }

    @Override
    public void __constructor__(Context context, AttributeSet attributeSet) {
        super.__constructor__(context, attributeSet);
    }

    @Override
    public void __constructor__(Context context, AttributeSet attributeSet, int defStyle) {
        super.__constructor__(context, attributeSet, defStyle);
    }

    @Implementation
    public boolean isItemChecked(int position) {
        if (checkedByPosition.containsKey(position)) {
            return checkedByPosition.get(position);
        }
        return false;
    }

    @Override
    @Implementation
    public void setItemChecked(int position, boolean value) {
        checkedByPosition.put(position, value);
    }

    @Implementation
    public int getCheckedItemCount() {
        return getTrueCheckedItemPositions().size();
    }

    private ArrayList<Integer> getTrueCheckedItemPositions() {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (Map.Entry<Integer, Boolean> check : checkedByPosition.entrySet()) {
            if (check.getValue() && check.getKey() >= 0) {
                positions.add(check.getKey());
            }
        }
        return positions;
    }

    @Implementation
    public long[] getCheckedItemIds() {
        ArrayList<Integer> positions = getTrueCheckedItemPositions();

        long[] checkedItemIds = new long[positions.size()];
        for (int i = 0; i < checkedItemIds.length; i++) {
            checkedItemIds[i] = super.getItemIdAtPosition(positions.get(i));
        }
        return checkedItemIds;
    }

}
