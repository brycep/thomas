package com.servolabs.thomas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class TrainingSessionDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsession_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(TrainingSessionDetailFragment.ARG_ITEM_ID,
                            getIntent().getParcelableExtra(TrainingSessionDetailFragment.ARG_ITEM_ID));
            TrainingSessionDetailFragment fragment = new TrainingSessionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.trainingsession_detail_container, fragment)
                            .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // NavUtils.navigateUpTo(this, new Intent(this, TrainingSessionListActivity.class));
            // The NavUtils method is supposed to do the same thing as the code below, but unit tests fail...
            Intent upIntent = new Intent(this, TrainingSessionListActivity.class);
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(upIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
